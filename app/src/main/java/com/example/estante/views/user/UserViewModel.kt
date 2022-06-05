package com.example.estante.views.user

import androidx.lifecycle.*
import com.example.estante.data.daos.UserDao
import com.example.estante.data.models.User
import com.example.estante.data.models.UserWithCollectionsAndComics
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val dao: UserDao) : ViewModel() {

//    private val _userList: MutableLiveData<List<User>> = MutableLiveData(
//        listOf(
//            User(
//                0,
//                "Rodrigo Oliveira da Silva",
//                0,
//            ),
//            User(
//                1,
//                "João",
//                0,
//            ),
//            User(
//                2,
//                "Maria",
//                0,
//            ),
//        )
//    )

    val allUsers: LiveData<List<User>> = dao.getUsers().asLiveData()

    val allUsersWithCollectionsAndComics: LiveData<List<UserWithCollectionsAndComics>> =
        dao.getUserWithCollectionsAndComics().asLiveData()

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
}

class UserViewModelFactory(private val dao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java))
            return UserViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}