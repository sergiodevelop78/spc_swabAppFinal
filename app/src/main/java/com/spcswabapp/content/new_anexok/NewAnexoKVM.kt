package com.spcswabapp.content.new_anexok

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.content.new_anexok.entities.TipsAnalisisRiesgoEntity
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.*
import com.spcswabapp.models.LoginData
import com.spcswabapp.services.SharedPreferencesManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewAnexoKVM(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val db: DatabaseMain
) : ViewModel() {

    private val _getDataSuccess = MutableLiveData<LoginData>()
    val getDataSuccess: LiveData<LoginData> get() = _getDataSuccess

    private val _createSwabReportSuccess = MutableLiveData<SwabReportEntity>()
    val createSwabReportSuccess: LiveData<SwabReportEntity> get() = _createSwabReportSuccess

    private val _getSwabUnitySuccess = MutableLiveData<ArrayList<SwabCriticalPointEntity>>()
    val getSwabUnitySuccess: LiveData<ArrayList<SwabCriticalPointEntity>> get() = _getSwabUnitySuccess

    private val _getTankSuccess = MutableLiveData<ArrayList<SwabCriticalPointEntity>>()
    val getTankSuccess: LiveData<ArrayList<SwabCriticalPointEntity>> get() = _getTankSuccess


    private val _getSwabReportSuccess = MutableLiveData<SwabReportEntity>()
    val getSwabReportSuccess: LiveData<SwabReportEntity> get() = _getSwabReportSuccess

    private val _getSwabFuelSuccess = MutableLiveData<SwabFuelEntity>()
    val getSwabFuelSuccess: LiveData<SwabFuelEntity> get() = _getSwabFuelSuccess

    private val _getSwabConsumptionDayliMaterials = MutableLiveData<SwabDayliConsumptionMaterialsEntity>()
    val getSwabConsumptionDayliMaterials : LiveData<SwabDayliConsumptionMaterialsEntity> get() = _getSwabConsumptionDayliMaterials

    private val _getSwabTankDispatch = MutableLiveData<ArrayList<SwabTankDispatchEntity>>()
    val getSwabTankDispatch : LiveData<ArrayList<SwabTankDispatchEntity>> get() = _getSwabTankDispatch

    private val _saveChangesSuccess = MutableLiveData<String>()
    val saveChangesSuccess : LiveData<String> get() = _saveChangesSuccess

    private val _getOrderSuccess = MutableLiveData<ArrayList<SwabReportEntity>>()
    val  getOrderSuccess : LiveData<ArrayList<SwabReportEntity>> get() = _getOrderSuccess

    private val _onDeleteSuccess = MutableLiveData<String>()
    val  onDeleteSuccess : LiveData<String> get() = _onDeleteSuccess

    @SuppressLint("CheckResult")
    fun deleteSwabReport(idSwabReport : Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.swabReportDao()
                val swabFuelDao = it.swabFuelDao()
                val swabCriticalPointDao = it.swabCriticalPointDao()
                val swabDayliConsumptionMaterialsDao = it.swabDayliConsumptionMaterialsDao()
                val swabTankDispatchDao = it.swabTankDispatchDao()

                /* SERGIO*/
                val getTipsAnalisisRiesgoDao = it.getTipsAnalisisRiesgoDao()

                swabTankDispatchDao.deleteSwabTankDispatch(idSwabReport)
                swabDayliConsumptionMaterialsDao.deleteSwabDayliConsumptionMaterial(idSwabReport)
                swabCriticalPointDao.deleteSwabCriticalPoint(idSwabReport)
                swabFuelDao.deleteSwabFuel(idSwabReport)
                reportDao.deleteSwabReport(idSwabReport)
                _onDeleteSuccess.postValue("Swab Report Eliminado con éxito")
            }
    }




    @SuppressLint("CheckResult")
    fun getSwabReport(idSwabReport: Int) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.swabReportDao()
                _getSwabReportSuccess.postValue(reportDao.getUserById(idSwabReport))
            }
        _getDataSuccess.value = sharedPreferencesManager.getUserData()
    }


    @SuppressLint("CheckResult")
    fun saveSwabReport(reportEntity: SwabReportEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.swabReportDao()
                reportDao.insertUser(reportEntity)
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            }
    }


    @SuppressLint("CheckResult")
    fun getSwabFuel(idSwabReport: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val fuelDao = it.swabFuelDao()
                _getSwabFuelSuccess.postValue(fuelDao.getById(idSwabReport))
            }
    }

    @SuppressLint("CheckResult", "SimpleDateFormat")
    fun saveSwabFuel(swabFuelEntity: SwabFuelEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val fuelDao = it.swabFuelDao()
                fuelDao.insertSwabFuel(swabFuelEntity)
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            }
    }


    @SuppressLint("CheckResult")
    fun getSwabCriticalPoint(idSwabReport: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val criticalPointSwab = it.swabCriticalPointDao()
                val list = ArrayList(criticalPointSwab.getSwabCriticalPointById(idSwabReport))
                _getSwabUnitySuccess.postValue(ArrayList(list.filter { s -> s.tipo == "1" }))
                _getTankSuccess.postValue(ArrayList(list.filter { s -> s.tipo == "2" }))
            },{
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun saveSwabCriticalPoint(swabCriticalPointList: ArrayList<SwabCriticalPointEntity>){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val criticalPointSwab = it.swabCriticalPointDao()
                for(swabCriticalPoint in swabCriticalPointList){
                    criticalPointSwab.insertSwabCriticalPoint(swabCriticalPoint)
                }
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            }
    }

    @SuppressLint("CheckResult")
    fun getSwabConsumptionDayliMaterials(idSwabReport: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val consumptionDayliMaterialsDao = it.swabDayliConsumptionMaterialsDao()
                _getSwabConsumptionDayliMaterials.postValue(consumptionDayliMaterialsDao.getSwabById(idSwabReport))
            },{
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun saveSwabConsumptionDayliMaterials(swabDayliConsumptionMaterialsEntity: SwabDayliConsumptionMaterialsEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val consumptionDayliMaterialsDao = it.swabDayliConsumptionMaterialsDao()
                consumptionDayliMaterialsDao.insertSwabDayliConsumptionMaterials(swabDayliConsumptionMaterialsEntity)
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            },{
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun getSwabTankDispatch(idSwabReport: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val tankDispatchDao = it.swabTankDispatchDao()
                val list = ArrayList(tankDispatchDao.getById(idSwabReport))
                _getSwabTankDispatch.postValue(list)
            },{
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun saveSwabTankDispatch(swabTankDispatchEntityList: ArrayList<SwabTankDispatchEntity>){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val tankDispatchDao = it.swabTankDispatchDao()
                for (tankDispatchItem in swabTankDispatchEntityList){
                    tankDispatchDao.insertSwabTankDispatch(tankDispatchItem)
                }
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            },{
                it.printStackTrace()
            })
    }


    @SuppressLint("CheckResult", "SimpleDateFormat")
    fun createNewSwabReport(isExist: Boolean, idSwabReport: Int) {

        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val reportDao = it.swabReportDao()
                val swabFuelDao = it.swabFuelDao()
                val swabCriticalPointDao = it.swabCriticalPointDao()
                val swabDayliConsumptionMaterialsDao = it.swabDayliConsumptionMaterialsDao()
                val swabTankDispatchDao = it.swabTankDispatchDao()
                val getTipsAnalisisRiesgoDao = it.getTipsAnalisisRiesgoDao()

                val data = sharedPreferencesManager.getUserData()
                val swabReportEntity: SwabReportEntity
                if (!isExist) {
                    val dataRegister: String =
                        SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(Date())
                    swabReportEntity = SwabReportEntity(
                        fechaRegistro = dataRegister,
                        idEstadoSwab = 1,
                        idUsuario = data!!.idUsuario.toInt(),
                        idEquipo = data.idEquipoTrabajo.toInt(),
                        idTurno = data.idTurno.toInt(),
                        comentarios = "",
                        csms = "",
                        fechaCierre = "",
                        idswab_produccion = ""

                    )
                    val id = reportDao.insertUser(
                        swabReportEntity
                    )

                    val swabFuelEntity = SwabFuelEntity(
                        idSwabReporte = id.toInt(),
                        galones = "",
                        hora = "",
                        horometroIni = "",
                        horometroFin = "",
                        observaciones = ""
                    )
                    swabFuelDao.insertSwabFuel(swabFuelEntity)

                    sharedPreferencesManager.getCriticalPointList()?.map { criticalPoint ->
                        val swabCriticalPoint = SwabCriticalPointEntity(
                            idSwabReporte = id.toInt(),
                            idPuntoCritico = criticalPoint.id.toInt(),
                            tipo = criticalPoint.type,
                            nombre = criticalPoint.name,
                            estadoPc = 1,
                            observaciones = ""
                        )
                        swabCriticalPointDao.insertSwabCriticalPoint(swabCriticalPoint)
                    }

                    val swabDayliConsumptionMaterials = SwabDayliConsumptionMaterialsEntity(
                        idswabReporte = id.toInt(),
                        mnrCopa1 = "",
                        mnrCopa2 = "",
                        mnrCopa3 = "",
                        mnrCopa4 = "",
                        mnrEco = "",
                        msCopa1 = "",
                        msCopa2 = "",
                        msCopa3 = "",
                        msCopa4 = "",
                        msEco = "",
                        mndCopa1 = "",
                        mndCopa2 = "",
                        mndCopa3 = "",
                        mndCopa4 = "",
                        mndEco = "",
                        mdaCopa1 = "",
                        mdaCopa2 = "",
                        mdaCopa3 = "",
                        mdaCopa4 = "",
                        mdaEco = ""
                    )

                    swabDayliConsumptionMaterialsDao.insertSwabDayliConsumptionMaterials(swabDayliConsumptionMaterials)

                    val swabTankDispatchEntity = SwabTankDispatchEntity(
                        idSwabReporte = id.toInt(),
                        nroCisterna = "",
                        horaSalida = "",
                        pozo1 = "",
                        cantidadDespachada = "",
                        lugarDeDescarga = "",
                        cisterna = "",
                        procedencia = "",
                        horaLlegada = "",
                        pozo2 = ""
                    )

                    val swabTankDispatchEntity2 = SwabTankDispatchEntity(
                        idSwabReporte = id.toInt(),
                        nroCisterna = "",
                        horaSalida = "",
                        pozo1 = "",
                        cantidadDespachada = "",
                        lugarDeDescarga = "",
                        cisterna = "",
                        procedencia = "",
                        horaLlegada = "",
                        pozo2 = ""
                    )

                    /* SERGIO */
                    val tipsAnlisisRiesgoEntity = TipsAnalisisRiesgoEntity(
                        idtips_analisisderiesgo = id.toInt(),
                        tips_analisisderiesgo = "",
                        grupo = "",
                        activo = 0,
                    )
                    getTipsAnalisisRiesgoDao.insertar(tipsAnlisisRiesgoEntity)


                    swabTankDispatchDao.insertSwabTankDispatch(swabTankDispatchEntity)
                    swabTankDispatchDao.insertSwabTankDispatch(swabTankDispatchEntity2)
                    swabReportEntity.idSwabReporte = id.toInt()
                } else {
                    swabReportEntity = reportDao.getUserById(idSwabReport)
                }
                _createSwabReportSuccess.postValue(swabReportEntity)


            },{
                it.printStackTrace()
            })

    }


}