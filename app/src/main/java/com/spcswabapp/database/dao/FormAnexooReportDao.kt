package com.spcswabapp.database.dao

import androidx.room.*
import com.spcswabapp.database.entities.FormAnexooReportEntity

@Dao
interface FormAnexooReportDao {
    @Query("SELECT * FROM form_anexoo")
    fun getAll(): List<FormAnexooReportEntity>

    @Query("SELECT * FROM form_anexoo ")
    fun getAllSync() : List<FormAnexooReportEntity>

    @Query("SELECT * FROM form_anexoo WHERE idform_anexoo = :idFormAnexoo")
    fun getById(idFormAnexoo: Int) : FormAnexooReportEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(swabReport : FormAnexooReportEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFormAnexoO(formAnexooReport : FormAnexooReportEntity): Long

    @Query("SELECT * FROM form_anexoo WHERE idform_anexoo = :idFormAnexoo")
    fun getUserById(idFormAnexoo:Int) : FormAnexooReportEntity

    @Query("UPDATE form_anexoo SET idform_anexoo_produccion=:idFormAnexooProduccion WHERE idform_anexoo = :idFormAnexoo")
    fun updateIDProduccion(idFormAnexoo:Int, idFormAnexooProduccion: String)

    @Query("DELETE FROM form_anexoo WHERE idform_anexoo = :idFormAnexoo")
    fun deleteSwabReport(idFormAnexoo:Int)
}