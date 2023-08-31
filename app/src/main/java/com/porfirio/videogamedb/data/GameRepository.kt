package com.porfirio.videogamedb.data

import com.porfirio.videogamedb.data.db.GameDao
import com.porfirio.videogamedb.data.db.model.GameEntity


class GameRepository (private val gameDao: GameDao) {

    suspend fun insertGame(game: GameEntity){
        gameDao.insertGame(game)
    }

    suspend fun getAllGames(): List<GameEntity> = gameDao.getAllGames()


    suspend fun updateGame(game: GameEntity){
        gameDao.updateGame(game)
    }

    suspend fun deleteGame(game: GameEntity){
        gameDao.deleteGame(game)
    }

}