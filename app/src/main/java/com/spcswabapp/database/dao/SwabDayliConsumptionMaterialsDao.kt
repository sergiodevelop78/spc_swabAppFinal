package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.SwabDayliConsumptionMaterialsEntity

@Dao
interface SwabDayliConsumptionMaterialsDao {
    @Query("SELECT * FROM swab_materiales_consumo")
    fun getAll(): List<SwabDayliConsumptionMaterialsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSwabDayliConsumptionMaterials(swabDayliConsumptionMaterialsEntity: SwabDayliConsumptionMaterialsEntity)

    @Query("SELECT * FROM swab_materiales_consumo WHERE idswab_reporte = :idSwabReport")
    fun getSwabById(idSwabReport: Int) : SwabDayliConsumptionMaterialsEntity

    @Query("DELETE FROM swab_materiales_consumo WHERE idswab_reporte = :idSwabReport")
    fun deleteSwabDayliConsumptionMaterial(idSwabReport:Int)
}