package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithCollectionsAndComics(
    @Embedded val user: User,
    @Relation(
        entity = Collection::class,
        parentColumn = "collections",
        entityColumn = "collectionId"
    )
    val collections: List<CollectionWithComics>
)
