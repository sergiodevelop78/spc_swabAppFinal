package com.spcswabapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "swab_incidencias")

@Entity(tableName = "swab_incidencias",
    indices = [Index(value = ["idswab_reporte", "idpozo"],
    unique = true)])

data class SwabIncidencesEntity(
    @SerializedName("idregistro")
    @PrimaryKey(autoGenerate = true) val idregistro : Int = 0,

    @SerializedName("idswab_reporte")
    @ColumnInfo(name = "idswab_reporte") val idSwabReporte: Int,

    @ColumnInfo(name = "idpozo") var idpozo: String,

    @SerializedName("pozo")
    @ColumnInfo(name = "pozo") var pozo: String,
    @SerializedName("bat")
    @ColumnInfo(name = "bat") var bat: String,
    @SerializedName("tips_reviso")
    @ColumnInfo(name = "tips_reviso") var tipsReviso: Int,
    @SerializedName("horas_presion")
    @ColumnInfo(name = "horas_presion") var horasPresion: String,
    @SerializedName("horas_inicio")
    @ColumnInfo(name = "horas_inicio") var horasInicio: String,
    @SerializedName("horas_md")
    @ColumnInfo(name = "horas_md") var horasMd: String,
    @SerializedName("horas_pist")
    @ColumnInfo(name = "horas_pist") var horasPist: String,
    @SerializedName("horas_mant")
    @ColumnInfo(name = "horas_mant") var horasMant: String,
    @SerializedName("horas_parada")
    @ColumnInfo(name = "horas_parada") var horasParada: String,
    @SerializedName("horas_termino")
    @ColumnInfo(name = "horas_termino") var horasTermino: String,
    @SerializedName("diam_cstb")
    @ColumnInfo(name = "diam_cstb") var diamCstb: String,
    @SerializedName("diam_na")
    @ColumnInfo(name = "diam_na") var diamNa: String,
    @SerializedName("niveles_inicial")
    @ColumnInfo(name = "niveles_inicial") var nivelesInicial: String,
    @SerializedName("niveles_final")
    @ColumnInfo(name = "niveles_final") var nivelesFinal: String,
    @SerializedName("corr")
    @ColumnInfo(name = "corr") var corr: String,
    @SerializedName("produccion_pet")
    @ColumnInfo(name = "produccion_pet") var produccionPet: String,
    @SerializedName("produccion_agua")
    @ColumnInfo(name = "produccion_agua") var produccionAgua: String,

    @ColumnInfo(name = "estadoApp") var estadoApp: Int,
    @ColumnInfo(name = "estadoIncidencia") var estadoIncidencia: Int,
    @ColumnInfo(name = "tieneAnexoK") var tieneAnexoK: Int

    )
