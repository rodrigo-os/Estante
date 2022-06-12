package com.example.estante.views.user

import androidx.lifecycle.*
import com.example.estante.data.daos.UserDao
import com.example.estante.data.models.User
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val dao: UserDao) : ViewModel() {
    val allUsers: LiveData<List<User>> = dao.getUsers().asLiveData()

    fun getUser(id: Int): User{
        allUsers.value?.forEach{
            if(id == it.userId){
                return it
            }
        }
        return User(
            -1,
            "",
            "",
        )
    }

    fun insert(user: User) {
        viewModelScope.launch {
            dao.insert(user)
        }
    }

    fun update(user: User) {
        viewModelScope.launch {
            dao.update(user)
        }
    }

    fun delete(user: User) {
        viewModelScope.launch {
            dao.delete(user)
        }
    }

    fun getStartPoint(): Int {
        return allUsers.value?.get(allUsers.value?.size ?: 0)?.userId ?: 0
    }
}

class UserViewModelFactory(private val dao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java))
            return UserViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}