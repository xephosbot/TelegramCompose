@file:Suppress("UNUSED")

package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi

/**
 * Suspend function, which changes the phone number of the user and sends an authentication code to
 * the user's new phone number. On success, returns information about the sent code.
 *
 * @param phoneNumber The new phone number of the user in international format.
 * @param settings Settings for the authentication of the user's phone number.
 *
 * @return [TdApi.AuthenticationCodeInfo] Information about the authentication code that was sent.
 */
suspend fun TelegramFlow.changePhoneNumber(
    phoneNumber: String?,
    settings: TdApi.PhoneNumberAuthenticationSettings?
): TdApi.AuthenticationCodeInfo = this.sendFunctionAsync(TdApi.ChangePhoneNumber(phoneNumber, settings))

/**
 * Suspend function, which checks the authentication code sent to confirm a new phone number of the
 * user.
 *
 * @param code Verification code received by SMS, phone call or flash call.
 */
suspend fun TelegramFlow.checkChangePhoneNumberCode(code: String?) =
    this.sendFunctionLaunch(TdApi.CheckChangePhoneNumberCode(code))

/**
 * Suspend function, which checks phone number confirmation code.
 *
 * @param code The phone number confirmation code.
 */
suspend fun TelegramFlow.checkPhoneNumberConfirmationCode(code: String?) =
    this.sendFunctionLaunch(TdApi.CheckPhoneNumberConfirmationCode(code))

/**
 * Suspend function, which checks the phone number verification code for Telegram Passport.
 *
 * @param code Verification code.
 */
suspend fun TelegramFlow.checkPhoneNumberVerificationCode(code: String?) =
    this.sendFunctionLaunch(TdApi.CheckPhoneNumberVerificationCode(code))

/**
 * Suspend function, which re-sends the authentication code sent to confirm a new phone number for
 * the user. Works only if the previously received authenticationCodeInfo nextCodeType was not null.
 *
 * @return [TdApi.AuthenticationCodeInfo] Information about the authentication code that was sent.
 */
suspend fun TelegramFlow.resendChangePhoneNumberCode(): TdApi.AuthenticationCodeInfo =
    this.sendFunctionAsync(TdApi.ResendChangePhoneNumberCode())

/**
 * Suspend function, which resends phone number confirmation code.
 *
 * @return [TdApi.AuthenticationCodeInfo] Information about the authentication code that was sent.
 */
suspend fun TelegramFlow.resendPhoneNumberConfirmationCode(): TdApi.AuthenticationCodeInfo =
    this.sendFunctionAsync(TdApi.ResendPhoneNumberConfirmationCode())

/**
 * Suspend function, which re-sends the code to verify a phone number to be added to a user's
 * Telegram Passport.
 *
 * @return [TdApi.AuthenticationCodeInfo] Information about the authentication code that was sent.
 */
suspend fun TelegramFlow.resendPhoneNumberVerificationCode(): TdApi.AuthenticationCodeInfo =
    this.sendFunctionAsync(TdApi.ResendPhoneNumberVerificationCode())

/**
 * Suspend function, which sends phone number confirmation code. Should be called when user presses
 * &quot;https://t.me/confirmphone?phone=*******&amp;hash=**********&quot; or
 * &quot;tg://confirmphone?phone=*******&amp;hash=**********&quot; link.
 *
 * @param hash Value of the &quot;hash&quot; parameter from the link.
 * @param phoneNumber Value of the &quot;phone&quot; parameter from the link.
 * @param settings Settings for the authentication of the user's phone number.
 *
 * @return [TdApi.AuthenticationCodeInfo] Information about the authentication code that was sent.
 */
suspend fun TelegramFlow.sendPhoneNumberConfirmationCode(
    hash: String?,
    phoneNumber: String?,
    settings: TdApi.PhoneNumberAuthenticationSettings?
): TdApi.AuthenticationCodeInfo = this.sendFunctionAsync(TdApi.SendPhoneNumberConfirmationCode(hash, phoneNumber, settings))

/**
 * Suspend function, which sends a code to verify a phone number to be added to a user's Telegram
 * Passport.
 *
 * @param phoneNumber The phone number of the user, in international format.
 * @param settings Settings for the authentication of the user's phone number.
 *
 * @return [TdApi.AuthenticationCodeInfo] Information about the authentication code that was sent.
 */
suspend fun TelegramFlow.sendPhoneNumberVerificationCode(
    phoneNumber: String?,
    settings: TdApi.PhoneNumberAuthenticationSettings?
): TdApi.AuthenticationCodeInfo = this.sendFunctionAsync(TdApi.SendPhoneNumberVerificationCode(phoneNumber, settings))

/**
 * Suspend function, which sets the phone number of the user and sends an authentication code to the
 * user. Works only when the current authorization state is authorizationStateWaitPhoneNumber, or if
 * there is no pending authentication query and the current authorization state is
 * authorizationStateWaitCode, authorizationStateWaitRegistration, or authorizationStateWaitPassword.
 *
 * @param phoneNumber The phone number of the user, in international format.
 * @param settings Settings for the authentication of the user's phone number.
 */
suspend fun TelegramFlow.setAuthenticationPhoneNumber(
    phoneNumber: String?,
    settings: TdApi.PhoneNumberAuthenticationSettings?
) = this.sendFunctionLaunch(TdApi.SetAuthenticationPhoneNumber(phoneNumber, settings))

/**
 * Suspend function, which shares the phone number of the current user with a mutual contact.
 * Supposed to be called when the user clicks on chatActionBarSharePhoneNumber.
 *
 * @param userId Identifier of the user with whom to share the phone number. The user must be a
 * mutual contact.
 */
suspend fun TelegramFlow.sharePhoneNumber(userId: Long) =
    this.sendFunctionLaunch(TdApi.SharePhoneNumber(userId))