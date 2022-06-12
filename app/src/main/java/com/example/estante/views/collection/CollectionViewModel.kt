package com.example.estante.views.collection

import androidx.lifecycle.*
import com.example.estante.data.daos.CollectionDao
import com.example.estante.data.models.Collection
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CollectionViewModel(private val dao: CollectionDao) : ViewModel() {
    val allCollections: LiveData<List<Collection>> = dao.getCollections().asLiveData()

    fun getCollection(id: Int): Collection {
        allCollections.value?.forEach {
            if (id == it.collectionId) {
                return it
            }
        }
        return Collection(
            -1,
            "",
            "",
        )
    }

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

    fun getStartPoint(): Int {
        return allCollections.value?.get(allCollections.value?.size ?: 0)?.collectionId ?: 0
    }
}

class CollectionViewModelFactory(private val dao: CollectionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionViewModel::class.java))
            return CollectionViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}