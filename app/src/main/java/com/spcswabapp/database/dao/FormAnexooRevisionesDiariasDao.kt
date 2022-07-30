package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.FormAnexooRevisionesDiariasEntity

@Dao
interface FormAnexooRevisionesDiariasDao {
    @Query("SELECT * FROM form_anexoo_revisiones_diarias")
    fun getAll(): List<FormAnexooRevisionesDiariasEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFormAnexooRevisionesDiarias(criticalPointEntity: FormAnexooRevisionesDiariasEntity)

    @Query("SELECT * FROM form_anexoo_revisiones_diarias WHERE idform_anexoo = :idFormAnexoo")
    fun getFormAnexooRevisionesDiariasById(idFormAnexoo: Int) : List<FormAnexooRevisionesDiariasEntity>


    @Query("DELETE FROM form_anexoo_revisiones_diarias WHERE idform_anexoo = :idFormAnexoo")
    fun deleteFormAnexooRevisionesDiarias(idFormAnexoo:Int)
}