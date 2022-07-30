package com.spcswabapp.content.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.SwabReportEntity
import com.spcswabapp.sergiofunciones.SergioFunciones
import com.spcswabapp.services.RetrofitApi
import com.spcswabapp.services.RetrofitConnection
import com.spcswabapp.services.SharedPreferencesManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

@Suppress("SpellCheckingInspection")

class HomeVM(
    val db: DatabaseMain,
    private val rc: RetrofitConnection,
    private val api: RetrofitApi,
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _getOrderSuccess = MutableLiveData<ArrayList<SwabReportEntity>>()
    val getOrderSuccess: LiveData<ArrayList<SwabReportEntity>> get() = _getOrderSuccess

    private val _onDeleteSuccess = MutableLiveData<String>()
    val onDeleteSuccess: LiveData<String> get() = _onDeleteSuccess

    private val sergioFunciones = SergioFunciones()


    @SuppressLint("CheckResult")
    fun getOrder() {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe({
                val orderDao = it.swabReportDao()
                val list = ArrayList(orderDao.getAll())
                val list2 = orderDao.getAll()
                sergioFunciones.showLogVerbose(
                    "SERGIO HomeVM -- CheckResult DAO GETALL = " + orderDao.getAll().toString()
                )
                sergioFunciones.showLogVerbose("SERGIO HomeVM -- CheckResult LIST2 = $list2")
                _getOrderSuccess.postValue(list)
            }, {
                print(it)
            })
    }

    var count = 0

    @SuppressLint("CheckResult")
    fun syncrhonize() {
        //sergioFunciones.showLogVerbose("Entrando a Sincronizar")

        var count = 0
        var context = this
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                //sergioFunciones.showLogVerbose("Entrando a Sincronizar Database")

                val orderDao = it.swabReportDao()
                val list = ArrayList(orderDao.getAllSync())
                //sergioFunciones.showLogVerbose("syncrhonize SwabReportEntity = "+list)
                sergioFunciones.showLogVerbose("syncrhonize SwabReportEntity list count= " + list.count())
                if (list.count() <= 0) {
                    var msg = "No existen órdenes completadas para enviar a la Web.\n"
                    msg += "Sólamente se pueden enviar órdenes en estado Finalizadas."
                    _onDeleteSuccess.postValue(msg)
                    return@subscribe
                }
                list.map { swabReportEntity ->
                    val data = HashMap<String, Any>()
                    val inspeccionList =
                        it.swabIncidencesDao().getAllById(swabReportEntity.idSwabReporte)
                    //sergioFunciones.showLogVerbose("syncrhonize SwabIncidencesEntity = "+inspeccionList)

                    val fuel = it.swabFuelDao().getById(swabReportEntity.idSwabReporte)
                    //sergioFunciones.showLogVerbose("syncrhonize SwabFuelEntity = "+fuel)

                    val material = it.swabDayliConsumptionMaterialsDao().getSwabById(
                        swabReportEntity.idSwabReporte
                    )
                    //sergioFunciones.showLogVerbose("syncrhonize SwabDayliConsumptionMaterialsEntity = "+material)

                    val criticalPointList = it.swabCriticalPointDao().getSwabCriticalPointById(
                        swabReportEntity.idSwabReporte
                    )
                    //sergioFunciones.showLogVerbose("syncrhonize SwabCriticalPointEntity = "+criticalPointList)

                    val tankList = it.swabTankDispatchDao().getById(swabReportEntity.idSwabReporte)
                    //sergioFunciones.showLogVerbose("syncrhonize SwabTankDispatchEntity = "+tankList)


                    data["data-swab-reporte"] = arrayListOf(swabReportEntity)

                    // Crear Trama para Inspecciones con Anexo K Incluido
                    val inspeccionesListFinal : MutableList<IncidenciasTrama> = mutableListOf()
                    val tmpInspeccionesList =  it.swabIncidencesDao().getAllById(swabReportEntity.idSwabReporte)
                    var i=0
                    tmpInspeccionesList.forEach { filaInsp ->
                        val anexokList = it.daoSwabTipsAnalisisRiesgo().getAllById(filaInsp.idregistro)
                        val anexoListLimpioFinal = mutableListOf<SwabTipsAnalsisRiesgoEntity>()
                        anexokList.forEach { fila ->
                            val anexoListLimpioTmp = SwabTipsAnalsisRiesgoEntity(
                                idregistro_incidencia = fila.idregistro_incidencia,
                                idtips_analisisderiesgo = fila.idtips_analisisderiesgo,
                                tips_analisisderiesgo = "",
                                grupo = "",
                                estado = fila.estado
                            )
                            anexoListLimpioFinal += anexoListLimpioTmp

                        }
                        val inspeccionesListTemp = IncidenciasTrama(
                           idregistro = filaInsp.idregistro,
                        idswab_reporte=  filaInsp.idSwabReporte,
                        idpozo=filaInsp.idpozo,
                        pozo=filaInsp.pozo,
                        bat=filaInsp.bat,
                        tips_reviso=filaInsp.tipsReviso,
                        horas_inicio=filaInsp.horasInicio,
                        horas_presion=filaInsp.horasPresion,
                        horas_md=filaInsp.horasMd,
                        horas_pist=filaInsp.horasPist,
                        horas_mant=filaInsp.horasMant,
                        horas_parada=filaInsp.horasParada,
                        horas_termino=filaInsp.horasTermino,
                        diam_cstb=filaInsp.diamCstb,
                        diam_na=filaInsp.diamNa,
                        niveles_inicial=filaInsp.nivelesInicial,
                        niveles_final=filaInsp.nivelesFinal,
                        corr=filaInsp.corr,
                        produccion_pet=filaInsp.produccionPet,
                        produccion_agua=filaInsp.produccionAgua,
                        estadoApp=filaInsp.estadoApp,
                        estadoIncidencia=filaInsp.estadoIncidencia,
                        tieneAnexoK=filaInsp.tieneAnexoK,
                        anexo_k=anexoListLimpioFinal
                        )
                        inspeccionesListFinal.add(inspeccionesListTemp)
                    }
                    data["data-inspecciones"] = inspeccionesListFinal

                    //val gsonInspeccionesNew = Gson().toJson(inspeccionesListFinal)
                    //sergioFunciones.logUnlimited("SERGIO", "gsonK=$gsonInspeccionesNew")
                    /* Fin creacion de trama Inspecciones con Anexo K Incluido */

                    //sergioFunciones.showLogVerbose("syncrhonize SwabTipsAnalsisRiesgoEntity 1 = "+anexoListLimpioFinal)

                    data["data-combustible"] = arrayListOf(fuel)
                    data["data-materiales"] = arrayListOf(material)

                    val pc = HashMap<String, Any>()
                    pc["pcs"] = criticalPointList
                    data["data-puntos-criticos"] = arrayListOf(pc)
                    data["data-despacho-cisterna"] = tankList

                    rc.connection(api.uploadDetails(data = data), success = {
                        Log.e("SERGIO", it.toString())
                        val errorResponse = it?.error.toString()
                        val dataResponse = it?.data.toString()
                        val errorResponseMessage = it?.message.toString()
                        Log.e("SERGIO", "Error = $errorResponse")

                        if (errorResponse == "true") {
                            count++
                            check(list.size, count)
                            _onDeleteSuccess.postValue("Ocurrió un error $errorResponseMessage")
                        } else {
                            if (it != null) {
                                if (!it.error) { // Sin errores
                                    Log.e("Sergio", "it error = " + it.error)
                                    deleteSwabReport(swabReportEntity.idSwabReporte, dataResponse)
                                }
                            }
                            count++
                            check(list.size, count)
                        }
                    }, error = {
                        //print("")
                        count++
                        check(list.size, count)
                        _onDeleteSuccess.postValue(it)

                    })
                }
            }
    }

    fun check(size: Int, count: Int) {
        if (count == size) {
            _onDeleteSuccess.postValue("Swab sincronizado Con exito")
        }
    }

    @SuppressLint("CheckResult")
    fun completeReport(swabReport: SwabReportEntity) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val swabReportDao = it.swabReportDao()
                swabReport.idEstadoSwab = 3
                swabReportDao.insertUser(swabReport)
                _onDeleteSuccess.postValue("Swab finalizado con exito")
                getOrder()
            }
    }


    @SuppressLint("CheckResult")
    fun deleteSwabReport(idSwabReport: Int, dataResponse: String) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.swabReportDao()
                val idEstadoSwab = reportDao.getById(idSwabReport).idEstadoSwab

                Log.e("Sergio", "### SwabReport "+reportDao.getById(idSwabReport))
                Log.e("Sergio", "### deleteSwabReport $idSwabReport")
                Log.e("Sergio", "### deleteSwabReport idEstadoSwab $idEstadoSwab")
                if (idEstadoSwab.toString() == "3") {  // Solo borrar los Estado = Finalizado
                    val swabFuelDao = it.swabFuelDao()
                    val swabCriticalPointDao = it.swabCriticalPointDao()
                    val swabDayliConsumptionMaterialsDao = it.swabDayliConsumptionMaterialsDao()
                    val swabTankDispatchDao = it.swabTankDispatchDao()
                    val swabIncidencesDao = it.swabIncidencesDao()
                    val daoSwabTipsAnalisisRiesgo = it.daoSwabTipsAnalisisRiesgo()

                    swabTankDispatchDao.deleteSwabTankDispatch(idSwabReport)
                    swabDayliConsumptionMaterialsDao.deleteSwabDayliConsumptionMaterial(idSwabReport)
                    swabCriticalPointDao.deleteSwabCriticalPoint(idSwabReport)
                    swabFuelDao.deleteSwabFuel(idSwabReport)
                    reportDao.deleteSwabReport(idSwabReport)

                    /* Recorrer tabla de Inspeccion o Inciencias, y borrar cada una */
                    val listIncidencesPorBorrar = swabIncidencesDao.getByIdRegistroToDeleteAnexoK(idSwabReport)
                    listIncidencesPorBorrar.forEach { fila ->
                        Log.e(
                            "Sergio",
                            "### idregistro borrar  = " + fila.idregistro
                        )
                        daoSwabTipsAnalisisRiesgo.deleteById(fila.idregistro)
                    }

                    /* Finalmente, borrar la Orden SWAB */
                    swabIncidencesDao.deleteById(idSwabReport)

                    /* Volver a Home */
                    getOrder()
                }
                else if (idEstadoSwab.toString() == "4") {  // Si sigue En Progreso (Envio parcial)
                    // Actualizar ID De BD Server en APP
                    val idSwabProduccion = dataResponse.toInt()
                    reportDao.updateIdServerBD(idSwabProduccion, idSwabReport)

                    /* Volver a Home */
                    getOrder()
                }

                //_onDeleteSuccess.postValue("Swab sincronizado con exito")
            }
    }


}