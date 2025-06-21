package ar.edu.unlam.scaffoldingandroid3.data.di

import ar.edu.unlam.scaffoldingandroid3.data.repository.LocalMonumentRepository
import ar.edu.unlam.scaffoldingandroid3.domain.services.GetMonumentosService
import ar.edu.unlam.scaffoldingandroid3.domain.usecases.GetMonumentosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Singleton
    @Provides
    fun provideGetMonumentosUseCase(monumentRepository: LocalMonumentRepository): GetMonumentosUseCase =
        GetMonumentosService(monumentRepository)
}
