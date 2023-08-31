package com.porfirio.videogamedb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.porfirio.videogamedb.Util.Constants
import com.porfirio.videogamedb.data.db.model.GameEntity

@Database(
    entities = [GameEntity :: class],
    version = 1, //versión de la base de datos, importante para las migraciones
    exportSchema = true // por defecto es true
)
abstract class GameDatabase: RoomDatabase(){ //Esta clase tiene que ser abstracta
    //Aqui va el DAO
    abstract fun gameDao(): GameDao

    //Sin inyección de dependencias, metemos la creación de la bd con un singleton (patrón de diseño)
    companion object{
        @Volatile  //lo que se escriba en este campo, será inmediatamente visible a otros hilos
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase{
            return INSTANCE?: synchronized(this){
                // Si la instancia no es nula, entonces se regresa, y
                // en caso contrario se crea la base de datos (patrón Singleton)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase :: class.java,
                    Constants.DATABASE_NAME
                ).fallbackToDestructiveMigration()  //Permite a Room recrear las tablas de la bd
                //si las migraciones no es encuentran
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
