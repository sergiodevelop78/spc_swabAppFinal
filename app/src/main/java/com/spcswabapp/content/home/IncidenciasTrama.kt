package com.spcswabapp.content.home

import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity

data class IncidenciasTrama (

    var idregistro : Int = 0,
    var idswab_reporte: Int,
    var idpozo: String,
    var pozo: String,
    var bat: String,
    var tips_reviso: Int,
    var horas_presion: String,
    var horas_inicio: String,
    var horas_md: String,
    var horas_pist: String,
    var horas_mant: String,
    var horas_parada: String,
    var horas_termino: String,
    var diam_cstb: String,
    var diam_na: String,
    var niveles_inicial: String,
    var niveles_final: String,
    var corr: String,
    var produccion_pet: String,
    var produccion_agua: String,

    var estadoApp: Int,
    var estadoIncidencia: Int,
    var tieneAnexoK: Int,

    var anexo_k: List<SwabTipsAnalsisRiesgoEntity>

)
