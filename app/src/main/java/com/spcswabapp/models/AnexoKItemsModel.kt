package com.spcswabapp.models

import com.google.gson.annotations.SerializedName

data class AnexoKItemsModel(
    @SerializedName("idtips_analisisderiesgo")
    val id : Int,

    @SerializedName("tips_analisisderiesgo")
    val name : String,

    @SerializedName("grupo")
    val grupo : String,

    @SerializedName("activo")
    val activo : Int,

    @SerializedName("estado")
    var estado : Int

)