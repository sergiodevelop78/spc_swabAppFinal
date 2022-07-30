package com.spcswabapp.content.new_anexok.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity

@Database(
    entities = [  SwabTipsAnalsisRiesgoEntity::class  ],
    version = 1
)

abstract class DBSergio : RoomDatabase(){

    companion object {
        @Volatile private var instance: DBSergio? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DBSergio::class.java, "sergiobd.db")
            .build()
    }



    abstract fun daoSwabTipsAnalisisRiesgo(): SwabTipsAnalisisRiesgoDao

}