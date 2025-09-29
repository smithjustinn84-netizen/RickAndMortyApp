package io.github.smithjustinn84_netizen.rickandmortyapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.smithjustinn84_netizen.rickandmortyapp.network.CharacterNetworkDataSource
import io.github.smithjustinn84_netizen.rickandmortyapp.network.NetworkDataSource
import javax.inject.Singleton

/**
 * Hilt module for providing data source dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    /**
     * Binds [CharacterNetworkDataSource] to [NetworkDataSource] interface.
     *
     * @param dataSource The [CharacterNetworkDataSource] instance.
     * @return An instance of [NetworkDataSource].
     */
    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: CharacterNetworkDataSource): NetworkDataSource
}
