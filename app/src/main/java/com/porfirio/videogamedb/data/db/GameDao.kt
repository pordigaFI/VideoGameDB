package com.porfirio.videogamedb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.porfirio.videogamedb.Util.Constants.DATABASE_GAME_TABLE
import com.porfirio.videogamedb.data.db.model.GameEntity

@Dao
interface GameDao {
    // aqui se definen las operaciones CRUD (Create, Read, Update y Delete)

    //Create
    @Insert
    suspend fun insertGame(game: GameEntity)  //suspend se usa cuando se va a utilizar co-rutinas
    //y se deja al sistema la posibilidad de suspender una función si así se requiere.

    //Cuando queremos instertar varios juegos se usa la siguiente instrucción
    @Insert
    suspend fun insertGames(games: List<GameEntity>)

    //Read
    @Query("SELECT * FROM ${DATABASE_GAME_TABLE}")
    suspend fun getAllGames(): List<GameEntity>

    // Update
    @Update
    suspend fun updateGame(game: GameEntity)

    //Delete
    @Delete
    suspend fun deleteGame(game: GameEntity)
}
