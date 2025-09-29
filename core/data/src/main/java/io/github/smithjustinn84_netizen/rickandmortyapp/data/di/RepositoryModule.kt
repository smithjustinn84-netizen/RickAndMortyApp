package io.github.smithjustinn84_netizen.rickandmortyapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.smithjustinn84_netizen.rickandmortyapp.data.CharacterRepoImpl
import io.github.smithjustinn84_netizen.rickandmortyapp.domain.CharacterRepo
import javax.inject.Singleton

/**
 * Hilt module for providing repository dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds [CharacterRepoImpl] to [CharacterRepo] interface.
     *
     * @param repository The [CharacterRepoImpl] instance.
     * @return An instance of [CharacterRepo].
     */
    @Singleton
    @Binds
    abstract fun bindCharacterRepo(repository: CharacterRepoImpl): CharacterRepo
}
