package com.spcswabapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spcswabapp.content.new_anexok.database.dao.SwabTipsAnalisisRiesgoDao
import com.spcswabapp.content.new_anexok.database.dao.TipsAnalisisRiesgoDao
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity
import com.spcswabapp.content.new_anexok.entities.TipsAnalisisRiesgoEntity
import com.spcswabapp.database.dao.*
import com.spcswabapp.database.entities.*
import com.spcswabapp.utils.Constants.Companion.DATABASE_NAME

@Database(
    entities = [SwabReportEntity::class, SwabFuelEntity::class,
        SwabIncidencesEntity::class, SwabTankDispatchEntity::class,
        SwabCriticalPointEntity::class, SwabDayliConsumptionMaterialsEntity::class,
        RevisionesDiariasEntity::class,  FormAnexooRevisionesDiariasEntity::class,
               FormAnexooReportEntity::class, FormAnexooIncidencesEntity::class,
        SwabTipsAnalsisRiesgoEntity::class,
    TipsAnalisisRiesgoEntity::class,
    PozosEntity::class
               ],
    version = 1
)


abstract class DatabaseMain : RoomDatabase() {
    abstract fun swabReportDao(): SwabReportDao
    abstract fun swabFuelDao(): SwabFuelDao
    abstract fun swabIncidencesDao(): SwabIncidencesDao
    abstract fun swabTankDispatchDao(): SwabTankDispatchDao
    abstract fun swabCriticalPointDao(): SwabCriticalPointDao
    abstract fun swabDayliConsumptionMaterialsDao(): SwabDayliConsumptionMaterialsDao

    /* SERGIO */
    abstract fun formAnexooReportDao(): FormAnexooReportDao
    abstract fun formAnexooRevisionesDiariasDao(): FormAnexooRevisionesDiariasDao
    abstract fun revisionesDiariasDao(): RevisionesDiariasDao
    abstract fun formAnexooIncidenciasDao(): FormAnexooIncidenciasDao

    abstract fun getTipsAnalisisRiesgoDao(): TipsAnalisisRiesgoDao
    abstract fun daoSwabTipsAnalisisRiesgo(): SwabTipsAnalisisRiesgoDao

    abstract fun getPozosDao(): PozosDao

    companion object {
        @Volatile private var instance: DatabaseMain? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DatabaseMain::class.java, DATABASE_NAME)
            .build()
    }

}


