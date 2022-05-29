package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comic")
data class Comic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val publisher: String,
)
