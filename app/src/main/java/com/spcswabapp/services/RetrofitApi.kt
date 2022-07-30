package com.spcswabapp.services

import com.spcswabapp.content.new_anexok.entities.TipsAnalisisRiesgoEntity
import com.spcswabapp.database.entities.*
import com.spcswabapp.models.*
import com.spcswabapp.utils.Constants
import io.reactivex.Observable
import retrofit2.http.*

interface RetrofitApi {

    @POST(Constants.LOGIN)
    fun login(@Body data : HashMap<String,String>) : Observable<ServerResponse<LoginData?>>

    @POST(Constants.SWAB_REPORTE)
    fun uploadSwabReport(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body swabReport : SwabReportEntity) : Observable<ServerResponse<SwabResponse>>

    @POST(Constants.INSERTAR_COMBUSTIBLE)
    fun uploadFuel(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body fuel : SwabFuelEntity) : Observable<ServerResponse<String>>

    @POST(Constants.INSERTAR_MATERIALES)
    fun uploadMaterials(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body materials : SwabDayliConsumptionMaterialsEntity) : Observable<ServerResponse<String>>

    @POST(Constants.INSERTAR_INCIDENCIAS)
    fun uploadIncidences(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body data : HashMap<String,Any>) : Observable<ServerResponse<String>>

    @POST(Constants.INSERTAR_PUNTOS_CRITICOS)
    fun uploadCriticalPoints(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body criticalPoint : HashMap<String,Any>) : Observable<ServerResponse<String>>

    @POST(Constants.REGISTER_DESPACHO_CISTERNAS)
    fun uploadTankDispatch(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body dispatchEntity: SwabTankDispatchEntity) : Observable<ServerResponse<String>>

    @POST(Constants.RECUPERAR_TABLA_PUNTOS_CRITICOS)
    fun getCriticalPoint(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body data : HashMap<String,String>) : Observable<ServerResponse<ArrayList<CriticalPoint>>>

    @POST(Constants.REGISTRAR_SWABDETALLES)
    fun uploadDetails(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body data : HashMap<String,Any>) : Observable<ServerResponse<String>>


    /* SERGIO */
    @POST(Constants.RECUPERAR_TABLA_REVISIONES_DIARIAS)
    fun getRevisionesDiarias(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body data : HashMap<String,String>) : Observable<ServerResponse<ArrayList<RevisionesDiarias>>>

    /* Funcion para sincronizar con WEB */
    @POST(Constants.REGISTRAR_FORMANEXOODETALLES)
    fun uploadDetailsFormAnexoo(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,@Body data : HashMap<String,Any>) : Observable<ServerResponse<String>>

    /* Funcion para traer la tabla tips_analisisderiesgo*/
    @POST(Constants.RECUPERAR_TABLA_TIPSANALISISRIESGO)
    fun getTipsAnalisisRiesgoRF(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,
                                @Body data : HashMap<String,String>) : Observable<ServerResponse<ArrayList<TipsAnalisisRiesgoEntity>>>

    /* Funcion para traer la tabla pozos*/
    @POST(Constants.RECUPERAR_TABLA_POZOS)
    fun getTablaPozos(@Header("Authorization") auth : String ?= Constants.AUTHORIZATIONCODE,
                      @Body data : HashMap<String,String>) : Observable<ServerResponse<ArrayList<PozosEntity>>>
}