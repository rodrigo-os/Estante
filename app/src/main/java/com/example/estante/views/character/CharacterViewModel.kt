package com.example.estante.views.character

import androidx.lifecycle.*
import com.example.estante.data.daos.CharacterDao
import com.example.estante.data.models.Character
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CharacterViewModel(private val dao: CharacterDao) : ViewModel() {
    val allCharacters: LiveData<List<Character>> = dao.getCharacters().asLiveData()

    fun getCharacter(id: Int): Character {
        allCharacters.value?.forEach{
            if(id == it.characterId){
                return it
            }
        }
        return Character(
            -1,
            "",
            "",
        )
    }

    fun insert(character: Character) {
        viewModelScope.launch {
            dao.insert(character)
        }
    }

    fun update(character: Character) {
        viewModelScope.launch {
            dao.update(character)
        }
    }

    fun delete(character: Character) {
        viewModelScope.launch {
            dao.delete(character)
        }
    }

    fun getStartPoint(): Int {
        return allCharacters.value?.get(allCharacters.value?.size ?: 0)?.characterId ?: 0
    }
}

class CharacterViewModelFactory(private val dao: CharacterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CharacterViewModel::class.java))
            return CharacterViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}