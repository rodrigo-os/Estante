package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class Collection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val creationDate: String,
    val comicsId: List<Int>,
    val favoriteComic: Int,
)
