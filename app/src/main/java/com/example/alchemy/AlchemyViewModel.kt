package com.example.alchemy

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlchemyViewModel(application: Application) : AndroidViewModel(application) {

    private val _alchemyRepository = MutableLiveData<AlchemyRepository?>()
    val alchemyRepository: LiveData<AlchemyRepository?> = _alchemyRepository

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                application,
                AppDatabase::class.java, "alchemy-database"
            ).build()
            _alchemyRepository.postValue(AlchemyRepository(db.ingredientDao()))
            Log.d("AlchemyViewModel", "alchemyRepository initialized")
        }
    }

    // Public method to set alchemyRepository for testing
    fun setAlchemyRepositoryForTesting(repository: AlchemyRepository) {
        _alchemyRepository.value = repository
    }
}

class AlchemyViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlchemyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlchemyViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}