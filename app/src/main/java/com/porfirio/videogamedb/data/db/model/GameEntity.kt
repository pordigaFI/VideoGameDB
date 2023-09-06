package com.porfirio.videogamedb.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.porfirio.videogamedb.Util.Constants

@Entity(tableName = Constants.DATABASE_GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val id: Long = 0,    //llave primaria
    @ColumnInfo(name= "game_title")
    var title: String,
    @ColumnInfo(name= "game_genre")
    var genre: String,
    @ColumnInfo(name= "game_developer")
    var developer: String
)
