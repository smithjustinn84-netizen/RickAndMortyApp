package io.github.smithjustinn84_netizen.rickandmortyapp.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.smithjustinn84_netizen.rickandmortyapp.database.AppDatabase
import io.github.smithjustinn84_netizen.rickandmortyapp.database.model.CharacterEntity
import io.github.smithjustinn84_netizen.rickandmortyapp.data.paging.pager
import io.github.smithjustinn84_netizen.rickandmortyapp.network.NetworkDataSource
import javax.inject.Singleton

/**
 * Hilt module to provide paging-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCharacterPager(
        appDatabase: AppDatabase,
        networkDataSource: NetworkDataSource,
        @ApplicationContext applicationContext: Context
    ): Pager<Int, CharacterEntity> =
        pager(appDatabase, networkDataSource, applicationContext)
}
