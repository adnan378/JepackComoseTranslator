package com.hypersoft.translator.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslationRepository @Inject constructor(private val checkInDao: TranslationDao) {


    fun getAllItems(): LiveData<List<TranslationHistory>> = checkInDao.getAllTranslation()

    suspend fun insertTranslation(checkIn: TranslationHistory) {
        withContext(Dispatchers.IO) {
            checkInDao.insertTranslation(checkIn)
        }
    }

    suspend  fun deleteTranslation(translation: TranslationHistory) {
       withContext(Dispatchers.IO){
           checkInDao.delete(translation)
       }
    }

    /* private val dao = database.translationDao()

    val allTranslation: LiveData<List<TranslationHistory>> = dao.getAllTranslation()

   suspend fun addTranslation(newTranslation: TranslationHistory)  {
       dao.insertTranslation(newTranslation)
    }

    suspend  fun deleteTranslation(translation: TranslationHistory) {
        dao.delete(translation)
    }*/

   /* suspend fun update(todo: TranslationHistory){
        todoDao.update(todo.id, todo.title, todo.note)
    } */
}