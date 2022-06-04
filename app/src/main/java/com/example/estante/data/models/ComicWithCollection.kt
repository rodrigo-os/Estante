package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ComicWithCollection(
    @Embedded val comic: Comic,
    @Relation(
        parentColumn = "collectionComicId",
        entityColumn = "collectionId",
    )
    val collection: Collection
//    val collections: List<Collection>

)
