package com.spcswabapp.database.dao

import android.database.sqlite.SQLiteException
import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.SwabIncidencesEntity

@Suppress("SpellCheckingInspection")

@Dao
interface SwabIncidencesDao {
    @Query("SELECT * FROM swab_incidencias")
    fun getAll(): List<SwabIncidencesEntity>

    @Query("SELECT * FROM swab_incidencias WHERE idregistro = :idRegistro ORDER BY idregistro ASC")
    fun getByIdRegistro(idRegistro: Int): SwabIncidencesEntity


    @Query("SELECT * FROM swab_incidencias WHERE idswab_reporte = :idSwabReport ORDER BY idregistro ASC")
    fun getByIdRegistroToDeleteAnexoK(idSwabReport: Int): List<SwabIncidencesEntity>

    @Query("SELECT * FROM swab_incidencias WHERE idswab_reporte = :idSwabReport ORDER BY idregistro ASC")
    fun getAllById(idSwabReport: Int): List<SwabIncidencesEntity>

    @Query("SELECT * FROM swab_incidencias WHERE idpozo = :idPozo and idswab_reporte = :idSwabReport ORDER BY idregistro ASC")
    fun getAllByIdPozo(idPozo: String, idSwabReport: Int): List<SwabIncidencesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //@WorkerThread
    fun insert(SwabIncidencesEntity: SwabIncidencesEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLiteException::class)
    fun insertConExcepcion(SwabIncidencesEntity: SwabIncidencesEntity)

    @Query("UPDATE swab_incidencias SET tieneAnexoK=1 WHERE idregistro=:idRegistro")
    fun updateTieneAnexoK(idRegistro: Int)

    @Query("SELECT * FROM swab_incidencias WHERE idpozo = :idPozo and idswab_reporte = :idSwabReport ORDER BY idregistro ASC")
    fun validarIncidenciasVacias(idPozo: String, idSwabReport: Int): List<SwabIncidencesEntity>

    @Query("DELETE FROM swab_incidencias WHERE idregistro = :idRegistro")
    fun deleteById(idRegistro: Int)
}