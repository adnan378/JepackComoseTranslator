package com.hypersoft.translator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hypersoft.translator.database.TranslationHistory
import com.hypersoft.translator.database.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val repository: TranslationRepository
) : ViewModel() {

    var readAllData: LiveData<List<TranslationHistory>> = repository.getAllItems()

    /* private val allHistory = MutableStateFlow(emptyList<TranslationHistory>())
     val allHistoryList: StateFlow<List<TranslationHistory>> = allHistory*/

    init {
        viewModelScope.launch {
//            allHistory.emit(repository.getAllItems())
        }
    }

    fun insertHistory(history: TranslationHistory) {
        viewModelScope.launch {
            repository.insertTranslation(history)
        }
    }

    fun deleteHistory(history: TranslationHistory) {
        viewModelScope.launch {
            repository.deleteTranslation(history)
        }
    }

    /*val allProducts: LiveData<List<TranslationHistory>>*/

    /*    val foundEmployee: LiveData<TranslationHistory> = translationRepository.foundEmployee */

   /* init {
        val translationDatabase = TranslationDatabase.getInstance()
        val productDao = translationDatabase.translationDao()
        repository = TranslationRepository(translationDatabase)
        allProducts = repository.allTranslation
    }

    fun insertHistory(history: TranslationHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTranslation(history)
        }
    }

    fun deleteHistory(todo: TranslationHistory) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTranslation(todo)
    }*/
}