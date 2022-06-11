package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
//    foreignKeys = [
//        ForeignKey(
//            entity = Collection::class,
//            parentColumns = arrayOf("collectionId"),
//            childColumns = arrayOf("collections"),
//            onDelete = ForeignKey.CASCADE
//        ),
//    ]
    )
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val name: String,
    val collections: String,
)
