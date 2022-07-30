package com.spcswabapp.models

import com.google.gson.annotations.SerializedName

data class AnexoK(
    @SerializedName("idtips_analisisderiesgo")
    val id : String,
    @SerializedName("tips_analisisderiesgo")
    val name : String,
    @SerializedName("grupo")
    val grupo : String,
    @SerializedName("activo")
    val type : String
)