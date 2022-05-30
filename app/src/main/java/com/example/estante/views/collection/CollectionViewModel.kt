package com.example.estante.views.collection

import androidx.lifecycle.*
import com.example.estante.data.daos.CollectionDao
import com.example.estante.data.models.Collection
import com.example.estante.data.models.CollectionWithComics
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CollectionViewModel(private val dao: CollectionDao) : ViewModel() {
    val allCollections: LiveData<List<Collection>> = dao.getCollections().asLiveData()

    val allCollectionsWithComics: LiveData<List<CollectionWithComics>> =
        dao.getCollectionWithComics().asLiveData()

    fun insert(collection: Collection) {
        viewModelScope.launch {
            dao.insert(collection)
        }
    }

    fun update(collection: Collection) {
        viewModelScope.launch {
            dao.update(collection)
        }
    }

    fun delete(collection: Collection) {
        viewModelScope.launch {
            dao.delete(collection)
        }
    }
}

class CollectionViewModelFactory(private val dao: CollectionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionViewModel::class.java))
            return CollectionViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}