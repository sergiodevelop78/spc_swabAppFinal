package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.SwabFuelEntity

@Dao
interface SwabFuelDao {
    @Query("SELECT * FROM swab_combustible")
    fun getAll(): List<SwabFuelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSwabFuel(swabFuel : SwabFuelEntity)

    @Query("SELECT * FROM swab_combustible WHERE idswab_reporte = :idSwabReport")
    fun getById(idSwabReport: Int) : SwabFuelEntity

    @Query("DELETE FROM swab_combustible WHERE idswab_reporte = :idSwabReport")
    fun deleteSwabFuel(idSwabReport:Int)
}