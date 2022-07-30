package com.spcswabapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spcswabapp.database.entities.PozosEntity
import com.spcswabapp.database.entities.RevisionesDiariasEntity

@Dao
interface PozosDao {

    @Query("SELECT * FROM pozos ORDER BY pozo ASC")
    fun getAll() : List<PozosEntity>

    @Query("SELECT * FROM pozos WHERE idpozo = :id")
    fun getAllById(id : Int) : List<PozosEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(pozosEntity: PozosEntity)

    @Query("DELETE FROM pozos WHERE idpozo = :id")
    fun delete(id:Int)

    @Query("DELETE FROM pozos")
    fun deleteAll()

}