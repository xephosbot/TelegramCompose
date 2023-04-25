//
// Copyright Aliaksei Levin (levlam@telegram.org), Arseny Smirnov (arseny30@gmail.com) 2014-2023
//
// Distributed under the Boost Software License, Version 1.0. (See accompanying
// file LICENSE_1_0.txt or copy at http://www.boost.org/LICENSE_1_0.txt)
//
package org.drinkless.tdlib;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Main class for interaction with the TDLib.
 */
public final class Client {
    static {
        try {
            System.loadLibrary("tdjni");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    /**
     * Interface for handler for results of queries to TDLib and incoming updates from TDLib.
     */
    public interface ResultHandler {
        /**
         * Callback called on result of query to TDLib or incoming update from TDLib.
         *
         * @param object Result of query or update of type TdApi.Update about new events.
         */
        void onResult(TdApi.Object object);
    }

    /**
     * Interface for handler of exceptions thrown while invoking ResultHandler.
     * By default, all such exceptions are ignored.
     * All exceptions thrown from ExceptionHandler are ignored.
     */
    public interface ExceptionHandler {
        /**
         * Callback called on exceptions thrown while invoking ResultHandler.
         *
         * @param e Exception thrown by ResultHandler.
         */
        void onException(Throwable e);
    }

    /**
     * Interface for handler of messages that are added to the internal TDLib log.
     */
    public interface LogMessageHandler {
        /**
         * Callback called on messages that are added to the internal TDLib log.
         *
         * @param verbosityLevel Log verbosity level with which the message was added from -1 up to 1024.
         *                       If 0, then TDLib will crash as soon as the callback returns.
         *                       None of the TDLib methods can be called from the callback.
         * @param message        The message added to the internal TDLib log.
         */
        void onLogMessage(int verbosityLevel, String message);
    }

    /**
     * Sends a request to the TDLib.
     *
     * @param query            Object representing a query to the TDLib.
     * @param resultHandler    Result handler with onResult method which will be called with result
     *                         of the query or with TdApi.Error as parameter. If it is null, nothing
     *                         will be called.
     * @param exceptionHandler Exception handler with onException method which will be called on
     *                         exception thrown from resultHandler. If it is null, then
     *                         defaultExceptionHandler will be called.
     * @throws NullPointerException if query is null.
     */
    public void send(TdApi.Function query, ResultHandler resultHandler, ExceptionHandler exceptionHandler) {
        long queryId = currentQueryId.incrementAndGet();
        if (resultHandler != null) {
            handlers.put(queryId, new Handler(resultHandler, exceptionHandler));
        }
        nativeClientSend(nativeClientId, queryId, query);
    }

    /**
     * Sends a request to the TDLib with an empty ExceptionHandler.
     *
     * @param query         Object representing a query to the TDLib.
     * @param resultHandler Result handler with onResult method which will be called with result
     *                      of the query or with TdApi.Error as parameter. If it is null, then
     *                      defaultExceptionHandler will be called.
     * @throws NullPointerException if query is null.
     */
    public void send(TdApi.Function query, ResultHandler resultHandler) {
        send(query, resultHandler, null);
    }

    /**
     * Synchronously executes a TDLib request. Only a few marked accordingly requests can be executed synchronously.
     *
     * @param query Object representing a query to the TDLib.
     * @return request result.
     * @throws NullPointerException if query is null.
     */
    public static TdApi.Object execute(TdApi.Function query) {
        return nativeClientExecute(query);
    }

    /**
     * Replaces handler for incoming updates from the TDLib.
     *
     * @param updatesHandler   Handler with onResult method which will be called for every incoming
     *                         update from the TDLib.
     * @param exceptionHandler Exception handler with onException method which will be called on
     *                         exception thrown from updatesHandler, if it is null, defaultExceptionHandler will be invoked.
     */
    public void setUpdatesHandler(ResultHandler updatesHandler, ExceptionHandler exceptionHandler) {
        handlers.put(0L, new Handler(updatesHandler, exceptionHandler));
    }
    /**
     * Replaces handler for incoming updates from the TDLib. Sets empty ExceptionHandler.
     *
     * @param updatesHandler Handler with onResult method which will be called for every incoming
     *                       update from the TDLib.
     */
    public void setUpdatesHandler(ResultHandler updatesHandler) {
        setUpdatesHandler(updatesHandler, null);
    }

    /**
     * Creates new Client.
     *
     * @param updateHandler           Handler for incoming updates.
     * @param updateExceptionHandler  Handler for exceptions thrown from updateHandler. If it is null, exceptions will be iggnored.
     * @param defaultExceptionHandler Default handler for exceptions thrown from all ResultHandler. If it is null, exceptions will be iggnored.
     * @return created Client
     */
    public static Client create(ResultHandler updateHandler, ExceptionHandler updateExceptionHandler, ExceptionHandler defaultExceptionHandler) {
        Client client = new Client(updateHandler, updateExceptionHandler, defaultExceptionHandler);
        synchronized (responseReceiver) {
            if (!responseReceiver.isRun) {
                responseReceiver.isRun = true;

                Thread receiverThread = new Thread(responseReceiver, "TDLib thread");
                receiverThread.setDaemon(true);
                receiverThread.start();
            }
        }
        return client;
    }

    /**
     * Sets the handler for messages that are added to the internal TDLib log.
     * None of the TDLib methods can be called from the callback.
     *
     * @param maxVerbosityLevel The maximum verbosity level of messages for which the callback will be called.
     * @param logMessageHandler Handler for messages that are added to the internal TDLib log. Pass null to remove the handler.
     */
    public static void setLogMessageHandler(int maxVerbosityLevel, Client.LogMessageHandler logMessageHandler) {
        nativeClientSetLogMessageHandler(maxVerbosityLevel, logMessageHandler);
    }

    private static class ResponseReceiver implements Runnable {
        public boolean isRun = false;

        @Override
        public void run() {
            while (true) {
                int resultN = nativeClientReceive(clientIds, eventIds, events, 100000.0 /*seconds*/);
                for (int i = 0; i < resultN; i++) {
                    processResult(clientIds[i], eventIds[i], events[i]);
                    events[i] = null;
                }
            }
        }

        private void processResult(int clientId, long id, TdApi.Object object) {
            boolean isClosed = false;
            if (id == 0 && object instanceof TdApi.UpdateAuthorizationState) {
                TdApi.AuthorizationState authorizationState = ((TdApi.UpdateAuthorizationState) object).authorizationState;
                if (authorizationState instanceof TdApi.AuthorizationStateClosed) {
                    isClosed = true;
                }
            }

            Handler handler = id == 0 ? updateHandlers.get(clientId) : handlers.remove(id);
            if (handler != null) {
                try {
                    handler.resultHandler.onResult(object);
                } catch (Throwable cause) {
                    ExceptionHandler exceptionHandler = handler.exceptionHandler;
                    if (exceptionHandler == null) {
                        exceptionHandler = defaultExceptionHandlers.get(clientId);
                    }
                    if (exceptionHandler != null) {
                        try {
                            exceptionHandler.onException(cause);
                        } catch (Throwable ignored) {
                        }
                    }
                }
            }

            if (isClosed) {
                updateHandlers.remove(clientId);           // there will be no more updates
                defaultExceptionHandlers.remove(clientId); // ignore further exceptions
                clientCount.decrementAndGet();
            }
        }

        private static final int MAX_EVENTS = 1000;
        private final int[] clientIds = new int[MAX_EVENTS];
        private final long[] eventIds = new long[MAX_EVENTS];
        private final TdApi.Object[] events = new TdApi.Object[MAX_EVENTS];
    }

    private final int nativeClientId;

    private static final ConcurrentHashMap<Integer, ExceptionHandler> defaultExceptionHandlers = new ConcurrentHashMap<Integer, ExceptionHandler>();
    private static final ConcurrentHashMap<Integer, Handler> updateHandlers = new ConcurrentHashMap<Integer, Handler>();
    private static final ConcurrentHashMap<Long, Handler> handlers = new ConcurrentHashMap<Long, Handler>();
    private static final AtomicLong currentQueryId = new AtomicLong();
    private static final AtomicLong clientCount = new AtomicLong();

    private static final ResponseReceiver responseReceiver = new ResponseReceiver();

    private static class Handler {
        final ResultHandler resultHandler;
        final ExceptionHandler exceptionHandler;

        Handler(ResultHandler resultHandler, ExceptionHandler exceptionHandler) {
            this.resultHandler = resultHandler;
            this.exceptionHandler = exceptionHandler;
        }
    }

    private Client(ResultHandler updateHandler, ExceptionHandler updateExceptionHandler, ExceptionHandler defaultExceptionHandler) {
        clientCount.incrementAndGet();
        nativeClientId = createNativeClient();
        if (updateHandler != null) {
            updateHandlers.put(nativeClientId, new Handler(updateHandler, updateExceptionHandler));
        }
        if (defaultExceptionHandler != null) {
            defaultExceptionHandlers.put(nativeClientId, defaultExceptionHandler);
        }
        send(new TdApi.GetOption("version"), null, null);
    }

    @Override
    protected void finalize() throws Throwable {
        send(new TdApi.Close(), null, null);
    }

    private static native int createNativeClient();

    private static native void nativeClientSend(int nativeClientId, long eventId, TdApi.Function function);

    private static native int nativeClientReceive(int[] clientIds, long[] eventIds, TdApi.Object[] events, double timeout);

    private static native TdApi.Object nativeClientExecute(TdApi.Function function);

    private static native void nativeClientSetLogMessageHandler(int maxVerbosityLevel, LogMessageHandler logMessageHandler);
}