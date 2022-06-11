package com.example.estante.views.comic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estante.data.models.Comic

class ComicViewModelSaveEdit : ViewModel() {
    private val _comicId: MutableLiveData<Int> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val publisher: MutableLiveData<String> = MutableLiveData()

    fun startBy(index: Int) {
        _comicId.value = index
    }

    fun insert(
        insertComic: (Comic) -> Unit
    ) {
        val newComic = Comic(
            _comicId.value ?: return,
            title.value ?: return,
            publisher.value ?: return,
        )
        insertComic(newComic)
        var newIndex = _comicId.value ?: return
        _comicId.value = newIndex + 1
        title.value = ""
        publisher.value = ""
    }

    fun update(
        id: Int,
        updateComic: (Comic) -> Unit
    ) {
        val comic = Comic(
            _comicId.value ?: return,
            title.value ?: return,
            publisher.value ?: return,
        )
        updateComic(comic)
    }
}