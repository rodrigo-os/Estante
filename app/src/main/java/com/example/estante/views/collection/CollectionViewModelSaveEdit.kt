package com.example.estante.views.collection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estante.data.models.Collection

class CollectionViewModelSaveEdit : ViewModel() {
    private val _collectionId: MutableLiveData<Int> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val note: MutableLiveData<String> = MutableLiveData()

    fun startBy(index: Int) {
        _collectionId.value = index
    }

    fun insert(
        insertCollection: (Collection) -> Unit
    ) {
        val newCollection = Collection(
            _collectionId.value ?: return,
            name.value ?: return,
            note.value ?: return,
        )
        insertCollection(newCollection)
        var newIndex = _collectionId.value ?: return
        _collectionId.value = newIndex + 1
        name.value = ""
        note.value = ""
    }

    fun update(
        id: Int,
        updateCollection: (Collection) -> Unit
    ) {
        val collection = Collection(
            _collectionId.value ?: return,
            name.value ?: return,
            note.value ?: return,
        )
        updateCollection(collection)
    }
}