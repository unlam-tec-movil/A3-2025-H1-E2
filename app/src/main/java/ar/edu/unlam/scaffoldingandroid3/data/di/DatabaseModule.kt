package ar.edu.unlam.scaffoldingandroid3.data.di

import android.content.Context
import androidx.room.Room
import ar.edu.unlam.scaffoldingandroid3.data.local.AppDatabase
import ar.edu.unlam.scaffoldingandroid3.data.local.MonumentoDatabase
import ar.edu.unlam.scaffoldingandroid3.data.local.dao.MonumentoDao
import ar.edu.unlam.scaffoldingandroid3.data.local.dao.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "photo_database",
            ).fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun providePhotoDao(db: AppDatabase): PhotoDao = db.photoDao()

    @Provides
    @Singleton
    fun provideMonumentDatabase(
        @ApplicationContext context: Context,
    ): MonumentoDatabase = Room.databaseBuilder(context, MonumentoDatabase::class.java, "monumentos-db").build()

    @Provides
    fun provideDao(db: MonumentoDatabase): MonumentoDao = db.monumentoDao()
}
