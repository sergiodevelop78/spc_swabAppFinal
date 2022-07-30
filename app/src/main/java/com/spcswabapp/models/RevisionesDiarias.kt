package com.spcswabapp.models

import com.google.gson.annotations.SerializedName

data class RevisionesDiarias(
    @SerializedName("idrevisiones_diarias")
    val id : String,
    @SerializedName("revision_diaria")
    val name : String,
    @SerializedName("activo")
    val type : String
)