package com.example.estante.views.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estante.data.models.Character

class AddEditCharacterViewModel : ViewModel(){
    private val _characterId: MutableLiveData<Int> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val creators: MutableLiveData<String> = MutableLiveData()

    fun startBy(index: Int){
        _characterId.value = index
    }

    fun insert(
        insertCharacter: (Character) -> Unit
    ) {
        val newCharacter = Character(
            _characterId.value?: return,
            name.value?: return,
            creators.value?: return,
        )
        insertCharacter(newCharacter)
        var newIndex = _characterId.value ?: return
        _characterId.value = newIndex + 1
        name.value = ""
        creators.value = ""
    }

    fun update(
        id: Int,
        updateCharacter: (Character) -> Unit
    ){
        val character = Character(
            id,
            name.value?: return,
            creators.value?: return,
        )
        updateCharacter(character)
    }
}