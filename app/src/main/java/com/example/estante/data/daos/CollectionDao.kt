package com.example.estante.data.daos

import androidx.room.*
import com.example.estante.data.models.Collection
import com.example.estante.data.models.CollectionWithComics
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection)

    @Update
    suspend fun update(collection: Collection)

    @Delete
    suspend fun delete(collection: Collection)

    @Query("SELECT * FROM collection")
    fun getCollections(): Flow<List<Collection>>

    @Transaction
    @Query("SELECT * FROM collection")
    fun getCollectionWithComics(): Flow<List<CollectionWithComics>>
}