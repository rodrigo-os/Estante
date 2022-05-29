package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ComicWithCollection(
    @Embedded val comic: Comic,
    @Relation(
        parentColumn = "comicId",
        entityColumn = "collectionId",
    )
    val collections: List<Collection>
)
