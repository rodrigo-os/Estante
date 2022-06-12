package com.example.estante.data.models

import androidx.room.Entity

@Entity(tableName = "comicCharacterCrossRef", primaryKeys = ["comicId", "characterId"])
data class ComicCharacterCrossRef(
    val comicId: Int,
    val characterId: Int,
)
