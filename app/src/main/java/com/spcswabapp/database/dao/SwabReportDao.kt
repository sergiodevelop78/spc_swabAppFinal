package com.spcswabapp.database.dao

import androidx.room.*
import com.spcswabapp.database.entities.SwabReportEntity

@Dao
interface SwabReportDao {
    @Query("SELECT * FROM swab_reporte")
    fun getAll(): List<SwabReportEntity>

    @Query("SELECT * FROM swab_reporte WHERE idestado_swab in (3,4)")
    fun getAllSync() : List<SwabReportEntity>

    @Query("SELECT * FROM swab_reporte WHERE idswab_reporte = :idSwabReport")
    fun getById(idSwabReport: Int) : SwabReportEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(swabReport : SwabReportEntity): Long

    @Query("SELECT * FROM swab_reporte WHERE idswab_reporte = :idSwabReport")
    fun getUserById(idSwabReport:Int) : SwabReportEntity

    @Update
    fun updateUsers(vararg swabReport: SwabReportEntity)

    @Query("DELETE FROM swab_reporte WHERE idswab_reporte = :idSwabReport")
    fun deleteSwabReport(idSwabReport:Int)

    @Query("UPDATE swab_reporte SET idestado_swab=4 WHERE idswab_reporte=:idSwabReport")
    fun updateEstadoProgreso( idSwabReport : Int)

    @Query("UPDATE swab_reporte SET idestado_swab=2 WHERE idswab_reporte=:idSwabReport")
    fun updateEstadoEnCurso( idSwabReport : Int)

    @Query("UPDATE swab_reporte SET idswab_produccion=:idProduccion WHERE idswab_reporte=:idSwabReport")
    fun updateIdServerBD(idProduccion: Int,  idSwabReport : Int)
}