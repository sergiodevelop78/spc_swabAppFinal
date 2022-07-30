package com.spcswabapp.content.new_anexok.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.content.new_anexok.entities.TipsAnalisisRiesgoEntity

@Dao
interface TipsAnalisisRiesgoDao {

    @Query("SELECT * FROM tips_analisisderiesgo ")
    suspend fun sgetAll() : List<TipsAnalisisRiesgoEntity>

    @Query("SELECT * FROM tips_analisisderiesgo where ACTIVO=1 ")
    fun getAll() : List<TipsAnalisisRiesgoEntity>

    @Query("SELECT * FROM tips_analisisderiesgo WHERE idtips_analisisderiesgo = :id")
    fun getAllById(id : Int) : List<TipsAnalisisRiesgoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(swabTipsAnalsisRiesgoEntity: TipsAnalisisRiesgoEntity)

    @Query("DELETE FROM tips_analisisderiesgo WHERE idtips_analisisderiesgo = :id")
    fun deleteSwabTipsAnalisisDeRiesgo(id:Int)

    @Query("DELETE FROM tips_analisisderiesgo")
    fun deleteAll()

}