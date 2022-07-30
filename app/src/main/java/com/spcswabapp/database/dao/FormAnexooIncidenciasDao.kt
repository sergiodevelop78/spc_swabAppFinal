package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.FormAnexooIncidencesEntity

@Dao
interface FormAnexooIncidenciasDao {
    @Query("SELECT * FROM form_anexoo_registros")
    fun getAll(): List<FormAnexooIncidencesEntity>

    @Query("SELECT * FROM form_anexoo_registros WHERE idform_anexoo_registros = :idRegistro")
    fun getByIdRegistro(idRegistro : Int) : FormAnexooIncidencesEntity

    @Query("SELECT * FROM form_anexoo_registros WHERE idform_anexoo = :idFormAnexoo")
    fun getAllById(idFormAnexoo : Int) : List<FormAnexooIncidencesEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(FormAnexooIncidencesEntity: FormAnexooIncidencesEntity)


    @Query("DELETE FROM form_anexoo_registros WHERE idform_anexoo_registros = :idRegistro")
    fun deleteById(idRegistro : Int)
}