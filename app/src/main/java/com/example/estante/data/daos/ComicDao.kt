package com.example.estante.data.daos

import androidx.room.*
import com.example.estante.data.models.Comic
import com.example.estante.data.models.ComicWithCharacters
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comic: Comic)

    @Update
    suspend fun update(comic: Comic)

    @Delete
    suspend fun delete(comic: Comic)

    @Query("SELECT * FROM comic")
    fun getComics(): Flow<List<Comic>>

    @Transaction
    @Query("SELECT * FROM comic")
    fun getComicWithCharacters(): Flow<List<ComicWithCharacters>>
}