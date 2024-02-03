package com.hypersoft.translator.di

import android.content.Context
import androidx.room.Room
import com.hypersoft.translator.database.TranslationDao
import com.hypersoft.translator.database.TranslationDatabase
import com.hypersoft.translator.database.TranslationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun getRepository(dao: TranslationDao): TranslationRepository {
        return TranslationRepository(dao)
    }

    @Singleton
    @Provides
    fun getDao(database: TranslationDatabase): TranslationDao {
        return database.translationDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TranslationDatabase {
        return Room.databaseBuilder(
            context.applicationContext, TranslationDatabase::class.java, "history_database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}
