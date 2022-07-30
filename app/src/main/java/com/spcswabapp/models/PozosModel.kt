package com.spcswabapp.models

import com.google.gson.annotations.SerializedName

data class PozosModel (
    @SerializedName("idpozo")
    val idpozo : Int,
    @SerializedName("pozo")
    val pozo : String,
    @SerializedName("bateria")
    val bateria : String,
    @SerializedName("diametro")
    val diametro : String,
    @SerializedName("diametro_fraccion")
    val diametro_fraccion : String,
    @SerializedName("na_td")
    val na_td : String,
    @SerializedName("activo")
    val activo : Int
)

