package io.github.smithjustinn84_netizen.rickandmortyapp.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.smithjustinn84_netizen.rickandmortyapp.network.httpClient
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Hilt module to provide networking-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides an [HttpClient] instance configured by the network module.
     */
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = httpClient()
}
