package com.xbot.telegramcompose.di

import android.content.Context
import com.xbot.tdlibx.core.TelegramFlow
import com.xbot.telegramcompose.data.AuthRepository
import com.xbot.telegramcompose.data.ChatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTelegramApi(): TelegramFlow {
        return TelegramFlow().apply { attachClient() }
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: TelegramFlow,
        @ApplicationContext context: Context
    ) = AuthRepository(api, context)

    @Singleton
    @Provides
    fun provideChatsRepository(api: TelegramFlow) = ChatsRepository(api)
}