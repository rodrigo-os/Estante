package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CollectionWithComics(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "collectionId",
        entityColumn = "collectionComicId",
    )
    val comics: List<Comic>
)
