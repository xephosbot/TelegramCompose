//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import kotlin.Long
import kotlin.String
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.InputCredentials
import org.drinkless.tdlib.TdApi.InputInvoice
import org.drinkless.tdlib.TdApi.PaymentForm
import org.drinkless.tdlib.TdApi.PaymentReceipt
import org.drinkless.tdlib.TdApi.PaymentResult
import org.drinkless.tdlib.TdApi.ThemeParameters

/**
 * Suspend function, which returns an invoice payment form. This method must be called when the user
 * presses inlineKeyboardButtonBuy.
 *
 * @param inputInvoice The invoice.  
 * @param theme Preferred payment form theme; pass null to use the default theme.
 *
 * @return [PaymentForm] Contains information about an invoice payment form.
 */
suspend fun TelegramFlow.getPaymentForm(inputInvoice: InputInvoice?, theme: ThemeParameters?):
    PaymentForm = this.sendFunctionAsync(TdApi.GetPaymentForm(inputInvoice, theme))

/**
 * Suspend function, which returns information about a successful payment.
 *
 * @param chatId Chat identifier of the messagePaymentSuccessful message.  
 * @param messageId Message identifier.
 *
 * @return [PaymentReceipt] Contains information about a successful payment.
 */
suspend fun TelegramFlow.getPaymentReceipt(chatId: Long, messageId: Long): PaymentReceipt =
    this.sendFunctionAsync(TdApi.GetPaymentReceipt(chatId, messageId))

/**
 * Suspend function, which sends a filled-out payment form to the bot for final verification.
 *
 * @param inputInvoice The invoice.  
 * @param paymentFormId Payment form identifier returned by getPaymentForm.  
 * @param orderInfoId Identifier returned by validateOrderInfo, or an empty string.  
 * @param shippingOptionId Identifier of a chosen shipping option, if applicable.  
 * @param credentials The credentials chosen by user for payment.  
 * @param tipAmount Chosen by the user amount of tip in the smallest units of the currency.
 *
 * @return [PaymentResult] Contains the result of a payment request.
 */
suspend fun TelegramFlow.sendPaymentForm(
  inputInvoice: InputInvoice?,
  paymentFormId: Long,
  orderInfoId: String?,
  shippingOptionId: String?,
  credentials: InputCredentials?,
  tipAmount: Long
): PaymentResult = this.sendFunctionAsync(TdApi.SendPaymentForm(inputInvoice, paymentFormId,
    orderInfoId, shippingOptionId, credentials, tipAmount))