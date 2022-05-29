package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val comicId: Int? = null,
)
