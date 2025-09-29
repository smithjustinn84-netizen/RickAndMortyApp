package io.github.smithjustinn84_netizen.rickandmortyapp.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.smithjustinn84_netizen.rickandmortyapp.database.AppDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.database.createAppDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.database.dao.CharacterDao
import javax.inject.Singleton

/**
 * Hilt module to provide database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        createAppDatabase(applicationContext)

    @Singleton
    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao =
        appDatabase.characterDao()
}
