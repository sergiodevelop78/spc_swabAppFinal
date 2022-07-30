package com.spcswabapp.content.new_anexok.Web
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity

data class TipsAnalisisRiesgoResponse(
    val error: String,
    val message: String,
    val data: List<SwabTipsAnalsisRiesgoEntity>
)
