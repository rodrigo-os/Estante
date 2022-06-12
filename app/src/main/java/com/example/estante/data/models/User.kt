package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user",)
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val name: String,
    val collections: String,
)
