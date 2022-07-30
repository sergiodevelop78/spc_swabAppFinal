package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.SwabTankDispatchEntity

@Dao
interface SwabTankDispatchDao {
    @Query("SELECT * FROM swab_despacho_cisternas")
    fun getAll(): List<SwabTankDispatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSwabTankDispatch(swabTankDispatchEntity: SwabTankDispatchEntity)

    @Query("SELECT * FROM swab_despacho_cisternas WHERE idswab_reporte = :idSwabReport")
    fun getById(idSwabReport: Int) : List<SwabTankDispatchEntity>

    @Query("DELETE FROM swab_despacho_cisternas WHERE idswab_reporte = :idSwabReport")
    fun deleteSwabTankDispatch(idSwabReport : Int)

}