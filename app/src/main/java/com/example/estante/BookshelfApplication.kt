package com.example.estante

import android.app.Application
import com.example.estante.data.BookshelfDatabase

class BookshelfApplication : Application() {

    val bookshelfDatabase: BookshelfDatabase by lazy {
        BookshelfDatabase.getInstance(this)
    }
}