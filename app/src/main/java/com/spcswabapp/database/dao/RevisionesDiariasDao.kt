package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.RevisionesDiariasEntity

@Dao
interface RevisionesDiariasDao {
    @Query("SELECT * FROM revisiones_diarias WHERE activo=1")
    fun getAll(): List<RevisionesDiariasEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFormAnexooRevisionesDiarias(criticalPointEntity: RevisionesDiariasEntity)

    @Query("SELECT * FROM revisiones_diarias WHERE idrevisiones_diarias = :idRevisionesDiarias")
    fun getFormAnexooRevisionesDiariasById(idRevisionesDiarias: Int) : List<RevisionesDiariasEntity>


    @Query("DELETE FROM revisiones_diarias WHERE idrevisiones_diarias = :idRevisionesDiarias")
    fun deleteFormAnexooRevisionesDiarias(idRevisionesDiarias:Int)
}