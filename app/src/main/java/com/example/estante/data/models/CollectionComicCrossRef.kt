package com.example.estante.data.models

import androidx.room.Entity

@Entity(primaryKeys = ["collectionId", "comicId"])
data class CollectionComicCrossRef(
    val collectionId: Int,
    val comicId: Int,
)
