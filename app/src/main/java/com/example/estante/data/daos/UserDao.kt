package com.example.estante.data.daos

import androidx.room.*
import com.example.estante.data.models.User
import com.example.estante.data.models.UserWithCollectionsAndComics
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<User>>

    @Transaction
    @Query("SELECT * FROM user")
    fun getUserWithCollectionsAndComics(): Flow<List<UserWithCollectionsAndComics>>
}