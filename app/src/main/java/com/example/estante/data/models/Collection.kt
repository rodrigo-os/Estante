package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class Collection(
    @PrimaryKey(autoGenerate = true) val collectionId: Int = 0,
    val collectionName: String,
    val note: String
)
