package com.spcswabapp.content.new_anexok.Web

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("getTableTipsAnalisisDeRiesgo")
    //suspend fun getAllTipsAnalisisRiesgo(@Body body : String): Response<TipsAnalisisRiesgoResponse>
    suspend fun getAllTipsAnalisisRiesgo(@Body accionEnvio: AccionEnvio): Response<TipsAnalisisRiesgoResponse>

    //@POST("/getTableTipsAnalisisDeRiesgo")
    //fun createStrawpoll(@Body accionEnvio: AccionEnvio): Call<*>

}

data class AccionEnvio(val accion: String, val valor: String)