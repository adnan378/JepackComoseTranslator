package com.hypersoft.translator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hypersoft.translator.MyApp

@Database(entities = [(TranslationHistory::class)], version = 1, exportSchema = false)

abstract class TranslationDatabase : RoomDatabase() {

    abstract fun translationDao(): TranslationDao

   /* companion object {
        *//*The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        It means that changes made by one thread to INSTANCE are visible to all other threads immediately.*//*
        @Volatile
        private var INSTANCE: TranslationDatabase? = null

        fun getInstance(): TranslationDatabase {
            // only one thread of execution at a time can enter this block of code
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        MyApp().getContext(),
                        TranslationDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }*/
}