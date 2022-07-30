package com.spcswabapp.content.form_anexoo

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.*
import com.spcswabapp.models.LoginData
import com.spcswabapp.services.SharedPreferencesManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewFormAnexoOVM(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val db: DatabaseMain
) : ViewModel() {


    private val _getDataSuccess = MutableLiveData<LoginData>()
    val getDataSuccess: LiveData<LoginData> get() = _getDataSuccess

    private val _createFormAnexooReportSuccess = MutableLiveData<FormAnexooReportEntity>()
    val createFormAnexooReportSuccess: LiveData<FormAnexooReportEntity> get() = _createFormAnexooReportSuccess

    private val _getSwabUnitySuccess = MutableLiveData<ArrayList<FormAnexooRevisionesDiariasEntity>>()
    val getSwabUnitySuccess: LiveData<ArrayList<FormAnexooRevisionesDiariasEntity>> get() = _getSwabUnitySuccess


    private val _getFormAnexoRevisionesDiariasSuccess = MutableLiveData<ArrayList<FormAnexooRevisionesDiariasEntity>>()
    val getFormAnexoRevisionesDiariasSuccess: LiveData<ArrayList<FormAnexooRevisionesDiariasEntity>> get() = _getFormAnexoRevisionesDiariasSuccess


    /*private val _getSwabReportSuccess = MutableLiveData<FormAnexooReportEntity>()
    val getSwabReportSuccess: LiveData<FormAnexooReportEntity> get() = _getSwabReportSuccess
    */

    private val _getFormAnexooReportSuccess = MutableLiveData<FormAnexooReportEntity>()
    val getFormAnexooReportSuccess: LiveData<FormAnexooReportEntity> get() = _getFormAnexooReportSuccess


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
                val swabCriticalPointDao = it.formAnexooRevisionesDiariasDao()
                swabCriticalPointDao.deleteFormAnexooRevisionesDiarias(idSwabReport)
                reportDao.deleteSwabReport(idSwabReport)
                _onDeleteSuccess.postValue("Form Anexo O Eliminado con éxito")
            }
    }


    @SuppressLint("CheckResult")
    fun getFormAnexooReport(idFormAnexoO: Int) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.formAnexooReportDao()
                _getFormAnexooReportSuccess.postValue(reportDao.getUserById(idFormAnexoO))
            }
        _getDataSuccess.value = sharedPreferencesManager.getUserData()
    }


    @SuppressLint("CheckResult")
    fun saveFormAnexooReport(reportEntity: FormAnexooReportEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.formAnexooReportDao()
                reportDao.insertFormAnexoO(reportEntity)
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            }
    }



    @SuppressLint("CheckResult")
    fun getSwabCriticalPoint(idSwabReport: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val criticalPointSwab = it.formAnexooRevisionesDiariasDao()
                val list = ArrayList(criticalPointSwab.getFormAnexooRevisionesDiariasById(idSwabReport))
                _getSwabUnitySuccess.postValue(ArrayList(list.filter { s -> s.tipo == "1" }))
            },{
                it.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    fun getFormAnexooRevisionesDiarias(idFormAnexoo: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val criticalPointSwab = it.formAnexooRevisionesDiariasDao()
                val list = ArrayList(criticalPointSwab.getFormAnexooRevisionesDiariasById(idFormAnexoo))
                _getFormAnexoRevisionesDiariasSuccess.postValue(ArrayList(list.filter { s -> s.tipo == "1" }))
            },{
                it.printStackTrace()
            })
    }

    /* POR BORRAR */
    @SuppressLint("CheckResult")
    fun saveSwabCriticalPoint(swabCriticalPointList: ArrayList<FormAnexooRevisionesDiariasEntity>){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val criticalPointSwab = it.formAnexooRevisionesDiariasDao()
                for(swabCriticalPoint in swabCriticalPointList){
                    criticalPointSwab.insertFormAnexooRevisionesDiarias(swabCriticalPoint)
                }
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            }
    }
    /**********/

    @SuppressLint("CheckResult")
    fun saveFormAnexooRevisionesDiarias(swabCriticalPointList: ArrayList<FormAnexooRevisionesDiariasEntity>){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val criticalPointSwab = it.formAnexooRevisionesDiariasDao()
                Log.e("Sergio", "--- swabCriticalPointList = $swabCriticalPointList")
                for(swabCriticalPoint in swabCriticalPointList){
                    criticalPointSwab.insertFormAnexooRevisionesDiarias(swabCriticalPoint)
                }
                _saveChangesSuccess.postValue("Datos guardados correctamente")
            }
    }

    @SuppressLint("CheckResult", "SimpleDateFormat")
    fun createNewFormAnexooReport(isExist: Boolean, idFormAnexoo: Int) {
        Log.e("SERGIO 2.1", "#### createNewFormAnexooReport")
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val reportDao = it.formAnexooReportDao()
                val formAnexooRevisionesDiariasDao = it.formAnexooRevisionesDiariasDao()

                val data = sharedPreferencesManager.getUserData()
                val formAnexooReportEntity: FormAnexooReportEntity

                //Log.d("SERGIO 2.2", "#### FormAnexooReportEntity")
                if (!isExist) {
                    Log.e("SERGIO 2.3", "#### isExists = "+isExist)
                    val dataRegister: String =
                        SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(Date())

                        formAnexooReportEntity = FormAnexooReportEntity(
                        fechaRegistro = dataRegister,
                        idEstado = 1,
                        idUsuario = data!!.idUsuario.toInt(),
                        idTurno = data.idTurno.toInt(),
                        macsObs = "",
                        cisterna = "",
                        fecha = "",
                        hora = "",
                        lugarRelevo = "",
                        nroPrecinto = "",
                        volumen = "",
                        idFormAnexoProduccion = ""

                    )

                    val id = reportDao.insertFormAnexoO(formAnexooReportEntity)

                    sharedPreferencesManager.getRevisionesDiariasList()?.map { revisionDiaria ->
                        try {
                            val formAnexooRevisionesDiarias = FormAnexooRevisionesDiariasEntity(
                                idFormAnexoo = id.toInt(),
                                idRevisionesDiarias = revisionDiaria.id.toInt(),
                                tipo = revisionDiaria.type,
                                nombre = revisionDiaria.name,
                                estado = 1,
                                observaciones = ""
                            )
                            Log.d("SERGIO 3.2", "#### formAnexooRevisionesDiarias creada.")
                            formAnexooRevisionesDiariasDao.insertFormAnexooRevisionesDiarias(
                                formAnexooRevisionesDiarias
                            )
                            Log.d("SERGIO 3.4", "#### RevDiaria="+ formAnexooRevisionesDiarias.nombre)
                            Log.d("SERGIO 3.4", "#### RevDiaria="+ formAnexooRevisionesDiarias.idRevisionesDiarias)
                        }
                        catch (exception: Exception) {
                            Log.d("SERGIO CATCH", exception.message.toString())
                        }
                    }

                    formAnexooReportEntity.idFormAnexoo = id.toInt()
                } else {
                    formAnexooReportEntity = reportDao.getUserById(idFormAnexoo)
                }
                _createFormAnexooReportSuccess.postValue(formAnexooReportEntity)


            },{
                it.printStackTrace()
            })

    }


}