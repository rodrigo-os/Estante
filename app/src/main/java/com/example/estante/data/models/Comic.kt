package com.example.estante.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "comic",
    foreignKeys = [
        ForeignKey(
            entity = Collection::class,
            parentColumns = arrayOf("collectionId"),
            childColumns = arrayOf("collectionComicId"),
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ]

)
data class Comic(
    @PrimaryKey(autoGenerate = true) val comicId: Int = 0,
    val title: String,
    val publisher: String,
    val collectionComicId: Int? = null
)
