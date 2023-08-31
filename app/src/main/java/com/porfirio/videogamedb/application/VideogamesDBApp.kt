package com.porfirio.videogamedb.application

import android.app.Application
import com.porfirio.videogamedb.data.GameRepository
import com.porfirio.videogamedb.data.db.GameDatabase

class VideogamesDBApp(): Application() {
    private val database by lazy{
        GameDatabase.getDatabase(this@VideogamesDBApp)
    }

    val repository by lazy{
        GameRepository(database.gameDao())
    }
}