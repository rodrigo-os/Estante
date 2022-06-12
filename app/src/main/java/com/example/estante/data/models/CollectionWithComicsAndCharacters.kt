package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class CollectionWithComicsAndCharacters(
    @Embedded val collection: Collection,
    @Relation(
        entity = Comic::class,
        parentColumn = "collectionId",
        entityColumn = "comicId"
    )
    val comics: List<ComicWithCharacters>
)
