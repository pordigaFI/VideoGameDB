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
    val title: String,
    @ColumnInfo(name= "game_genre")
    val genre: String,
    @ColumnInfo(name= "game_developer")
    val developer: String
)
