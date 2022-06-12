package com.example.estante.data.daos

import androidx.room.*
import com.example.estante.data.models.Character
import com.example.estante.data.models.CharacterWithComic
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Update
    suspend fun update(character: Character)

    @Delete
    suspend fun delete(character: Character)

    @Query("SELECT * FROM character")
    fun getCharacters(): Flow<List<Character>>

    @Transaction
    @Query("SELECT * FROM character")
    fun getCharacterWithComic(): Flow<List<CharacterWithComic>>
}