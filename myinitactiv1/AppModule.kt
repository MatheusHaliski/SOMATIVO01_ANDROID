package com.example.myinitactiv1

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // O módulo será instalado no componente Singleton
object AppModule {

    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}
