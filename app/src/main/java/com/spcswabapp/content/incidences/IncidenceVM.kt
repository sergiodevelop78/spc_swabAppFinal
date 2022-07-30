package com.spcswabapp.content.incidences

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.SwabIncidencesEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

@Suppress("SpellCheckingInspection")
class IncidenceVM(private val db: DatabaseMain) : ViewModel() {

    private val _getIncidencesSuccess = MutableLiveData<ArrayList<SwabIncidencesEntity>>()
    val getIncidencesSuccess: LiveData<ArrayList<SwabIncidencesEntity>> get() = _getIncidencesSuccess

    private val _addIncidenceSuccess = MutableLiveData<String>()
    val addIncidenceSuccess: LiveData<String> get() = _addIncidenceSuccess

    private val _getStadoSwab = MutableLiveData<Boolean>()
    val getStadoSwab: LiveData<Boolean> get() = _getStadoSwab


    @SuppressLint("CheckResult")
    fun getIncidences(idSwabReport: Int) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.swabIncidencesDao()
                val reportDao = it.swabReportDao()
                val report = reportDao.getById(idSwabReport)

                val list = ArrayList(incidencesDao.getAllById(idSwabReport))

                var validaTieneAnexoK = 0

                // Validar que todas las inspecciones tengan Anexo K
                list.forEach { rowIncidencia ->
                    Log.e("Sergio", ">>> IncidenceVM - rowIncidencia = $rowIncidencia")
                    if (rowIncidencia.tieneAnexoK == 1)
                        validaTieneAnexoK += 1
                }

                Log.e("Sergio", ">>> IncidenceVM - list Size = "+list.size)
                Log.e("SERGIO", ">>> IncidenceVM - List = $list")
                if (list.size == 0) {
                    report.idEstadoSwab = 1
                } else {
                    if (report.idEstadoSwab == 4 && validaTieneAnexoK < list.size)
                        report.idEstadoSwab = 2
                    else
                        report.idEstadoSwab = report.idEstadoSwab
                }

                Log.e("Sergio", ">>> IncidenceVM - validaTieneAnexoK = $validaTieneAnexoK")


                /*if(list.size > 0 && report.idEstadoSwab != 3){
                    report.idEstadoSwab = 2
                }
                else if(list.size == 0 && report.idEstadoSwab != 3){
                    report.idEstadoSwab = 1
                }

                */
                reportDao.insertUser(report)
                if (report.idEstadoSwab == 3) {
                    _getStadoSwab.postValue(true)
                } else {
                    _getStadoSwab.postValue(false)
                }

                _getIncidencesSuccess.postValue(list)
            }
    }

    @SuppressLint("CheckResult")
    fun addIncidence(swabIncidencesEntity: SwabIncidencesEntity) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                try {
                    val incidencesDao = it.swabIncidencesDao()
                    /* Sergio - Validar que no se ingrese registro en blanco */
                    val idPozo = swabIncidencesEntity.idpozo
                    val idSwabReport = swabIncidencesEntity.idSwabReporte
                    val validarIncidenciasRegUnicos = incidencesDao.validarIncidenciasVacias(idPozo, idSwabReport)
                    val contar = validarIncidenciasRegUnicos.size
                    if (contar<1) {
                        incidencesDao.insertConExcepcion(swabIncidencesEntity)
                        _addIncidenceSuccess.postValue("Inspección agregada con éxito")
                    }
                    else {
                        val msgerror2 = "No se puede insertar una Inspección Repetida. Valide que no haya inspecciones vacías."
                        _addIncidenceSuccess.postValue(msgerror2)
                    }
                }
                catch (erro: SQLiteException) {
                    val msgerror = erro.message
                    val msgerror2 = "Ocurrio un error: No se puede insertar una Inspección nueva sin haber completado una anterior."
                    _addIncidenceSuccess.postValue("Error = $msgerror2")
                }
            }


    }


}