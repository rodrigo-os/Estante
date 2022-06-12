package com.example.estante.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.estante.data.daos.CollectionDao
import com.example.estante.data.daos.ComicDao
import com.example.estante.data.daos.CharacterDao
import com.example.estante.data.models.Character
import com.example.estante.data.models.Collection
import com.example.estante.data.models.Comic

@Database(
    entities = [Collection::class, Comic::class,Character::class],
    version = 1,
    exportSchema = false,
)
abstract class BookshelfDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
    abstract fun comicDao(): ComicDao
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: BookshelfDatabase? = null

        fun getInstance(context: Context): BookshelfDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BookshelfDatabase::class.java,
                    "bookshelf_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}