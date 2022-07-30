package com.spcswabapp.content

import android.app.Application
import androidx.room.Room
import com.spcswabapp.content.new_anexok.database.dao.DBSergio

class SergioNotas: Application() {

    companion object {
        lateinit var database: DBSergio
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, DBSergio::class.java, "sergiodb").build()
    }
}