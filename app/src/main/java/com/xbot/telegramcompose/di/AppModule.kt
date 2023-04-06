package com.xbot.telegramcompose.di

import android.content.Context
import android.os.Build
import androidx.compose.ui.text.intl.Locale
import com.xbot.telegramcompose.BuildConfig
import com.xbot.telegramcompose.R
import com.xbot.telegramcompose.data.ChatsRepository
import com.xbot.telegramcompose.data.TelegramRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTdlibParameters(@ApplicationContext context: Context): TdApi.TdlibParameters {
        return TdApi.TdlibParameters().apply {
            // Obtain application identifier hash for Telegram API access at https://my.telegram.org
            apiId = context.resources.getInteger(R.integer.telegram_api_id)
            apiHash = context.getString(R.string.telegram_api_hash)
            useMessageDatabase = true
            useChatInfoDatabase = true
            useSecretChats = false
            systemLanguageCode = Locale.current.language
            databaseDirectory = context.filesDir.absolutePath
            deviceModel = Build.MODEL
            systemVersion = Build.VERSION.RELEASE
            applicationVersion = BuildConfig.VERSION_NAME
            enableStorageOptimizer = true
            useTestDc = false
        }
    }

    @Singleton
    @Provides
    fun provideTelegramRepository(parameters: TdApi.TdlibParameters) = TelegramRepository(parameters)

    @Singleton
    @Provides
    fun provideChatsRepository(api: TelegramRepository) = ChatsRepository(api)
}