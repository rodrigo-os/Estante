package com.example.estante.views.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estante.data.models.User

class UserViewModelSaveEdit : ViewModel(){

    private val _userId: MutableLiveData<Int> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val collections: MutableLiveData<String> = MutableLiveData()

    fun startBy(index: Int){
        _userId.value = index
    }

    fun insert(
        insertUser: (User) -> Unit
    ){
        val newUser = User(
            _userId.value?: return,
            name.value?: return,
            collections.value?: return,

        )
        insertUser(newUser)
        var newIndex = _userId.value ?: return
        _userId.value = newIndex + 1
        name.value = ""
        collections.value = ""
    }

    fun update(
        id: Int,
        updateUser: (User) -> Unit
    ){
        val user = User(
//            _userId.value ?: return,
            id, // revisar aqui
            name.value?: return,
            collections.value?: return,
        )
        updateUser(user)
    }
}