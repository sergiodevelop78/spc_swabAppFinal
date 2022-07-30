package com.spcswabapp.content.new_anexok.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity

@Dao
interface SwabTipsAnalisisRiesgoDao {

    @Query("SELECT * FROM swab_tips_analisisderiesgo ")
    suspend fun sgetAll() : List<SwabTipsAnalsisRiesgoEntity>

    @Query("SELECT * FROM swab_tips_analisisderiesgo ")
    fun getAll() : List<SwabTipsAnalsisRiesgoEntity>

    @Query("SELECT * FROM swab_tips_analisisderiesgo WHERE idregistro_incidencia = :idregistro_incidencia")
    fun getAllById(idregistro_incidencia : Int) : List<SwabTipsAnalsisRiesgoEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(swabTipsAnalsisRiesgoEntity: SwabTipsAnalsisRiesgoEntity)

    @Query("DELETE FROM swab_tips_analisisderiesgo WHERE idregistro_incidencia = :idregistro_incidencia")
    fun deleteSwabTipsAnalisisDeRiesgo(idregistro_incidencia:Int)

    @Query("DELETE FROM swab_tips_analisisderiesgo")
    fun deleteAll()

    @Query("DELETE FROM swab_tips_analisisderiesgo WHERE idregistro_incidencia = :idregistro_incidencia ")
    fun deleteById(idregistro_incidencia : Int)
}