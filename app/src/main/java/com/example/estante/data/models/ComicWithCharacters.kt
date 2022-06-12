package com.example.estante.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ComicWithCharacters(
    @Embedded val comic: Comic,
    @Relation(
        parentColumn = "comicId",
        entityColumn = "comicCharacterId",
    )
    val characters: List<Character>
)
