package com.spcswabapp.models

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("idusuario")
    val idUsuario: String,
    val username: String,
    val nombres: String,
    val apellidos: String,
    @SerializedName("idperfil")
    val idPerfil: String,
    val email: String,
    val appAndroid: String,
    val activo: String,
    val perfilDesc: String,
    @SerializedName("idequipo_trabajo")
    val idEquipoTrabajo: String,
    @SerializedName("equipo_trabajoDesc")
    val equipoTrabajoDesc: String,
    @SerializedName("idturno")
    val idTurno: String,
    val turnoDesc: String,

    @SerializedName("app_modulo")
    val appModulo: String
)