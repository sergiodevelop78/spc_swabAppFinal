package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.SwabCriticalPointEntity

@Dao
interface SwabCriticalPointDao {
    @Query("SELECT * FROM swab_puntos_criticos")
    fun getAll(): List<SwabCriticalPointEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSwabCriticalPoint(criticalPointEntity: SwabCriticalPointEntity)

    @Query("SELECT * FROM swab_puntos_criticos WHERE idswab_reporte = :idSwabReport")
    fun getSwabCriticalPointById(idSwabReport: Int) : List<SwabCriticalPointEntity>


    @Query("DELETE FROM swab_puntos_criticos WHERE idswab_reporte = :idSwabReport")
    fun deleteSwabCriticalPoint(idSwabReport:Int)
}