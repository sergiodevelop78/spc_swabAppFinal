package com.spcswabapp.models

import com.google.gson.annotations.SerializedName

data class CriticalPoint(
    @SerializedName("idpunto_critico")
    val id : String,
    @SerializedName("punto_critico")
    val name : String,
    @SerializedName("tipo")
    val type : String
)