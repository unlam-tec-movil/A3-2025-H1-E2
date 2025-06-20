package ar.edu.unlam.scaffoldingandroid3.data.di

import ar.edu.unlam.scaffoldingandroid3.data.repository.LocalMonumentRepository
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.MonumentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindMonumentRepository(monumentRepository: LocalMonumentRepository): MonumentRepository
}
