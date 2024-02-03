package com.hypersoft.translator.database

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class TranslationHistory(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "first_language")
    val firstLanguage: String,
    @ColumnInfo(name = "second_language")
    val secondLanguage: String,
    @ColumnInfo(name = "first_text")
    val firstText: String,
    @ColumnInfo(name = "second_text")
    val secondText: String
)
@Dao
interface TranslationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertTranslation(history: TranslationHistory)
    @Delete
    suspend fun delete(translation: TranslationHistory)
    @Query("SELECT * from translationhistory")
    fun getAllTranslation(): LiveData<List<TranslationHistory>>

}