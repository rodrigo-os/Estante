package com.example.estante.views.comic

import androidx.lifecycle.*
import com.example.estante.data.daos.ComicDao
import com.example.estante.data.models.Collection
import com.example.estante.data.models.Comic
import com.example.estante.data.models.ComicWithCollection
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ComicViewModel(private val dao: ComicDao) : ViewModel() {
    val allComics: LiveData<List<Comic>> = dao.getComics().asLiveData()

    /**
     * Não esta funcionando - Preciso revisar !!!
        val allComicsWithCollections: LiveData<List<ComicWithCollection>> =
        dao.getComicWithCollection().asLiveData()
     */

    fun insert(comic: Comic) {
        viewModelScope.launch {
            dao.insert(comic)
        }
    }

    fun update(comic: Comic) {
        viewModelScope.launch {
            dao.update(comic)
        }
    }

    fun delete(comic: Comic) {
        viewModelScope.launch {
            dao.delete(comic)
        }
    }

    fun getComic(id: Int): Comic {
        allComics.value?.forEach {
            if (id == it.comicId) {
                return it
            }
        }
        return Comic(
            -1,
            "",
            "",
        )
    }
    /**
     * Não esta funcionando - Preciso revisar !!!
        fun getComicWithCollection(id: Int): Comic {
            allComicsWithCollections.value?.forEach {
                if (id == it.comic.comicId) {
                    return it.comic
                }
            }
            return Comic(
                -1,
                "",
                "",
            )
        }
     */

    fun getStartPoint(): Int {
        return allComics.value?.get(allComics.value?.size ?: 0)?.comicId ?: 0
    }
}

class ComicViewModelFactory(private val dao: ComicDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicViewModel::class.java))
            return ComicViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}