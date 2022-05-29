package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithCollectionsAndComics(
    @Embedded val user: User,
    @Relation(
        entity = Collection::class,
        parentColumn = "userId",
        entityColumn = "collectionCreatorId"
    )
    val collections: List<CollectionWithComics>
)
