package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithComic(
    @Embedded val character: Character,
    @Relation(
        parentColumn = "comicCharacterId",
        entityColumn = "comicId",
    )
    val comic: Comic

)
