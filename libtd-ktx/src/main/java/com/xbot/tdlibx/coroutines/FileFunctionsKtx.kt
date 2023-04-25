//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.Error
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.FileDownloadedPrefixSize
import org.drinkless.tdlib.TdApi.FilePart
import org.drinkless.tdlib.TdApi.FileType
import org.drinkless.tdlib.TdApi.FoundFileDownloads
import org.drinkless.tdlib.TdApi.InputFile
import org.drinkless.tdlib.TdApi.Location
import org.drinkless.tdlib.TdApi.MessageFileType
import org.drinkless.tdlib.TdApi.StickerFormat
import org.drinkless.tdlib.TdApi.Text

/**
 * Suspend function, which adds a file from a message to the list of file downloads. Download
 * progress and completion of the download will be notified through updateFile updates. If message
 * database is used, the list of file downloads is persistent across application restarts. The
 * downloading is independent from download using downloadFile, i.e. it continues if downloadFile is
 * canceled or is used to download a part of the file.
 *
 * @param fileId Identifier of the file to download.  
 * @param chatId Chat identifier of the message with the file.  
 * @param messageId Message identifier.  
 * @param priority Priority of the download (1-32). The higher the priority, the earlier the file
 * will be downloaded. If the priorities of two files are equal, then the last one for which
 * downloadFile/addFileToDownloads was called will be downloaded first.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.addFileToDownloads(
  fileId: Int,
  chatId: Long,
  messageId: Long,
  priority: Int
): File = this.sendFunctionAsync(TdApi.AddFileToDownloads(fileId, chatId, messageId, priority))

/**
 * Suspend function, which stops the downloading of a file. If a file has already been downloaded,
 * does nothing.
 *
 * @param fileId Identifier of a file to stop downloading.  
 * @param onlyIfPending Pass true to stop downloading only if it hasn&#039;t been started, i.e.
 * request hasn&#039;t been sent to server.
 */
suspend fun TelegramFlow.cancelDownloadFile(fileId: Int, onlyIfPending: Boolean) =
    this.sendFunctionLaunch(TdApi.CancelDownloadFile(fileId, onlyIfPending))

/**
 * Suspend function, which stops the preliminary uploading of a file. Supported only for files
 * uploaded by using preliminaryUploadFile. For other files the behavior is undefined.
 *
 * @param fileId Identifier of the file to stop uploading.
 */
suspend fun TelegramFlow.cancelPreliminaryUploadFile(fileId: Int) =
    this.sendFunctionLaunch(TdApi.CancelPreliminaryUploadFile(fileId))

/**
 * Suspend function, which removes potentially dangerous characters from the name of a file. The
 * encoding of the file name is supposed to be UTF-8. Returns an empty string on failure. Can be called
 * synchronously.
 *
 * @param fileName File name or path to the file.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.cleanFileName(fileName: String?): Text =
    this.sendFunctionAsync(TdApi.CleanFileName(fileName))

/**
 * Suspend function, which deletes a file from the TDLib file cache.
 *
 * @param fileId Identifier of the file to delete.
 */
suspend fun TelegramFlow.deleteFile(fileId: Int) = this.sendFunctionLaunch(TdApi.DeleteFile(fileId))

/**
 * Suspend function, which downloads a file from the cloud. Download progress and completion of the
 * download will be notified through updateFile updates.
 *
 * @param fileId Identifier of the file to download.  
 * @param priority Priority of the download (1-32). The higher the priority, the earlier the file
 * will be downloaded. If the priorities of two files are equal, then the last one for which
 * downloadFile/addFileToDownloads was called will be downloaded first.  
 * @param offset The starting position from which the file needs to be downloaded.  
 * @param limit Number of bytes which need to be downloaded starting from the &quot;offset&quot;
 * position before the download will automatically be canceled; use 0 to download without a limit.  
 * @param synchronous Pass true to return response only after the file download has succeeded, has
 * failed, has been canceled, or a new downloadFile request with different offset/limit parameters was
 * sent; pass false to return file state immediately, just after the download has been started.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.downloadFile(
  fileId: Int,
  priority: Int,
  offset: Long,
  limit: Long,
  synchronous: Boolean
): File = this.sendFunctionAsync(TdApi.DownloadFile(fileId, priority, offset, limit, synchronous))

/**
 * Suspend function, which finishes the file generation.
 *
 * @param generationId The identifier of the generation process.  
 * @param error If passed, the file generation has failed and must be terminated; pass null if the
 * file generation succeeded.
 */
suspend fun TelegramFlow.finishFileGeneration(generationId: Long, error: Error?) =
    this.sendFunctionLaunch(TdApi.FinishFileGeneration(generationId, error))

/**
 * Suspend function, which returns information about a file; this is an offline request.
 *
 * @param fileId Identifier of the file to get.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.getFile(fileId: Int): File = this.sendFunctionAsync(TdApi.GetFile(fileId))

/**
 * Suspend function, which returns file downloaded prefix size from a given offset, in bytes.
 *
 * @param fileId Identifier of the file.  
 * @param offset Offset from which downloaded prefix size needs to be calculated.
 *
 * @return [FileDownloadedPrefixSize] Contains size of downloaded prefix of a file.
 */
suspend fun TelegramFlow.getFileDownloadedPrefixSize(fileId: Int, offset: Long):
    FileDownloadedPrefixSize = this.sendFunctionAsync(TdApi.GetFileDownloadedPrefixSize(fileId,
    offset))

/**
 * Suspend function, which returns the extension of a file, guessed by its MIME type. Returns an
 * empty string on failure. Can be called synchronously.
 *
 * @param mimeType The MIME type of the file.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getFileExtension(mimeType: String?): Text =
    this.sendFunctionAsync(TdApi.GetFileExtension(mimeType))

/**
 * Suspend function, which returns the MIME type of a file, guessed by its extension. Returns an
 * empty string on failure. Can be called synchronously.
 *
 * @param fileName The name of the file or path to the file.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getFileMimeType(fileName: String?): Text =
    this.sendFunctionAsync(TdApi.GetFileMimeType(fileName))

/**
 * Suspend function, which returns information about a file with a map thumbnail in PNG format. Only
 * map thumbnail files with size less than 1MB can be downloaded.
 *
 * @param location Location of the map center.  
 * @param zoom Map zoom level; 13-20.  
 * @param width Map width in pixels before applying scale; 16-1024.  
 * @param height Map height in pixels before applying scale; 16-1024.  
 * @param scale Map scale; 1-3.  
 * @param chatId Identifier of a chat in which the thumbnail will be shown. Use 0 if unknown.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.getMapThumbnailFile(
  location: Location?,
  zoom: Int,
  width: Int,
  height: Int,
  scale: Int,
  chatId: Long
): File = this.sendFunctionAsync(TdApi.GetMapThumbnailFile(location, zoom, width, height, scale,
    chatId))

/**
 * Suspend function, which returns information about a file with messages exported from another
 * application.
 *
 * @param messageFileHead Beginning of the message file; up to 100 first lines.
 *
 * @return [MessageFileType] This class is an abstract base class.
 */
suspend fun TelegramFlow.getMessageFileType(messageFileHead: String?): MessageFileType =
    this.sendFunctionAsync(TdApi.GetMessageFileType(messageFileHead))

/**
 * Suspend function, which returns information about a file by its remote ID; this is an offline
 * request. Can be used to register a URL as a file for further uploading, or sending as a message.
 * Even the request succeeds, the file can be used only if it is still accessible to the user. For
 * example, if the file is from a message, then the message must be not deleted and accessible to the
 * user. If the file database is disabled, then the corresponding object with the file must be
 * preloaded by the application.
 *
 * @param remoteFileId Remote identifier of the file to get.  
 * @param fileType File type; pass null if unknown.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.getRemoteFile(remoteFileId: String?, fileType: FileType?): File =
    this.sendFunctionAsync(TdApi.GetRemoteFile(remoteFileId, fileType))

/**
 * Suspend function, which returns suggested name for saving a file in a given directory.
 *
 * @param fileId Identifier of the file.  
 * @param directory Directory in which the file is supposed to be saved.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getSuggestedFileName(fileId: Int, directory: String?): Text =
    this.sendFunctionAsync(TdApi.GetSuggestedFileName(fileId, directory))

/**
 * Suspend function, which preliminary uploads a file to the cloud before sending it in a message,
 * which can be useful for uploading of being recorded voice and video notes. Updates updateFile will
 * be used to notify about upload progress and successful completion of the upload. The file will not
 * have a persistent remote identifier until it will be sent in a message.
 *
 * @param file File to upload.  
 * @param fileType File type; pass null if unknown.  
 * @param priority Priority of the upload (1-32). The higher the priority, the earlier the file will
 * be uploaded. If the priorities of two files are equal, then the first one for which
 * preliminaryUploadFile was called will be uploaded first.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.preliminaryUploadFile(
  file: InputFile?,
  fileType: FileType?,
  priority: Int
): File = this.sendFunctionAsync(TdApi.PreliminaryUploadFile(file, fileType, priority))

/**
 * Suspend function, which reads a part of a file from the TDLib file cache and returns read bytes.
 * This method is intended to be used only if the application has no direct access to TDLib&#039;s file
 * system, because it is usually slower than a direct read from the file.
 *
 * @param fileId Identifier of the file. The file must be located in the TDLib file cache.  
 * @param offset The offset from which to read the file.  
 * @param count Number of bytes to read. An error will be returned if there are not enough bytes
 * available in the file from the specified position. Pass 0 to read all available data from the
 * specified position.
 *
 * @return [FilePart] Contains a part of a file.
 */
suspend fun TelegramFlow.readFilePart(
  fileId: Int,
  offset: Long,
  count: Long
): FilePart = this.sendFunctionAsync(TdApi.ReadFilePart(fileId, offset, count))

/**
 * Suspend function, which removes all files from the file download list.
 *
 * @param onlyActive Pass true to remove only active downloads, including paused.  
 * @param onlyCompleted Pass true to remove only completed downloads.  
 * @param deleteFromCache Pass true to delete the file from the TDLib file cache.
 */
suspend fun TelegramFlow.removeAllFilesFromDownloads(
  onlyActive: Boolean,
  onlyCompleted: Boolean,
  deleteFromCache: Boolean
) = this.sendFunctionLaunch(TdApi.RemoveAllFilesFromDownloads(onlyActive, onlyCompleted,
    deleteFromCache))

/**
 * Suspend function, which removes a file from the file download list.
 *
 * @param fileId Identifier of the downloaded file.  
 * @param deleteFromCache Pass true to delete the file from the TDLib file cache.
 */
suspend fun TelegramFlow.removeFileFromDownloads(fileId: Int, deleteFromCache: Boolean) =
    this.sendFunctionLaunch(TdApi.RemoveFileFromDownloads(fileId, deleteFromCache))

/**
 * Suspend function, which searches for files in the file download list or recently downloaded files
 * from the list.
 *
 * @param query Query to search for; may be empty to return all downloaded files.  
 * @param onlyActive Pass true to search only for active downloads, including paused.  
 * @param onlyCompleted Pass true to search only for completed downloads.  
 * @param offset Offset of the first entry to return as received from the previous request; use
 * empty string to get the first chunk of results.  
 * @param limit The maximum number of files to be returned.
 *
 * @return [FoundFileDownloads] Contains a list of downloaded files, found by a search.
 */
suspend fun TelegramFlow.searchFileDownloads(
  query: String?,
  onlyActive: Boolean,
  onlyCompleted: Boolean,
  offset: String?,
  limit: Int
): FoundFileDownloads = this.sendFunctionAsync(TdApi.SearchFileDownloads(query, onlyActive,
    onlyCompleted, offset, limit))

/**
 * Suspend function, which informs TDLib on a file generation progress.
 *
 * @param generationId The identifier of the generation process.  
 * @param expectedSize Expected size of the generated file, in bytes; 0 if unknown.  
 * @param localPrefixSize The number of bytes already generated.
 */
suspend fun TelegramFlow.setFileGenerationProgress(
  generationId: Long,
  expectedSize: Long,
  localPrefixSize: Long
) = this.sendFunctionLaunch(TdApi.SetFileGenerationProgress(generationId, expectedSize,
    localPrefixSize))

/**
 * Suspend function, which uploads a file with a sticker; returns the uploaded file.
 *
 * @param userId Sticker file owner; ignored for regular users.  
 * @param stickerFormat Sticker format.  
 * @param sticker File file to upload; must fit in a 512x512 square. For WEBP stickers the file must
 * be in WEBP or PNG format, which will be converted to WEBP server-side. See
 * https://core.telegram.org/animated_stickers#technical-requirements for technical requirements.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.uploadStickerFile(
  userId: Long,
  stickerFormat: StickerFormat?,
  sticker: InputFile?
): File = this.sendFunctionAsync(TdApi.UploadStickerFile(userId, stickerFormat, sticker))

/**
 * Suspend function, which writes a part of a generated file. This method is intended to be used
 * only if the application has no direct access to TDLib&#039;s file system, because it is usually
 * slower than a direct write to the destination file.
 *
 * @param generationId The identifier of the generation process.  
 * @param offset The offset from which to write the data to the file.  
 * @param data The data to write.
 */
suspend fun TelegramFlow.writeGeneratedFilePart(
  generationId: Long,
  offset: Long,
  data: ByteArray?
) = this.sendFunctionLaunch(TdApi.WriteGeneratedFilePart(generationId, offset, data))
