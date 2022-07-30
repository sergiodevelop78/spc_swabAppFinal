package com.spcswabapp.content.incidences.detail

import android.annotation.SuppressLint
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.FormAnexooRevisionesDiariasEntity
import com.spcswabapp.database.entities.PozosEntity
import com.spcswabapp.database.entities.SwabIncidencesEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

@Suppress("SpellCheckingInspection")
class IncidenceDetailVM(private val db: DatabaseMain) : ViewModel() {

    private val _deleteSuccess = MutableLiveData<String>()
    val deleteSuccess: LiveData<String> get() = _deleteSuccess

    private val _getIncidenceSuccess = MutableLiveData<SwabIncidencesEntity>()
    val getIncidenceSuccess: LiveData<SwabIncidencesEntity> get() = _getIncidenceSuccess

    private val _saveSuccess = MutableLiveData<String>()
    val saveSuccess: LiveData<String> get() = _saveSuccess

    private val _saveError = MutableLiveData<String>()
    val saveError: LiveData<String> get() = _saveError

    /* SERGIO*/
    private val _getPozosSucessX = MutableLiveData<List<PozosEntity>>()
    val getPozosSucessX: LiveData<List<PozosEntity>> get() = _getPozosSucessX
    /* FIN SERGIO */

    private val _getPozosSucess = MutableLiveData<ArrayList<PozosEntity>>()
    val getPozosSucess: LiveData<ArrayList<PozosEntity>> get() = _getPozosSucess


    @SuppressLint("CheckResult")
    fun deleteIncidence(idRegistro: Int) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.swabIncidencesDao()
                val daoSwabTipsAnalisisRiesgoDao = it.daoSwabTipsAnalisisRiesgo()
                incidencesDao.deleteById(idRegistro)
                daoSwabTipsAnalisisRiesgoDao.deleteById(idRegistro)
                _deleteSuccess.postValue("Inspección eliminada")
            }
    }
    /*@SuppressLint("CheckResult")
    fun saveIncidence(swabIncidencesEntity: SwabIncidencesEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.swabIncidencesDao()
                incidencesDao.insert(swabIncidencesEntity)
                _saveSuccess.postValue("Inspección guardada con éxito")
            }
    }
    */

    @SuppressLint("CheckResult")
    fun saveIncidence(swabIncidencesEntity: SwabIncidencesEntity, editable: Boolean, datosCompletos: Boolean) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                var esRescribible = false
                var idRegistroEncontrado: Int = 0
                val incidencesDao = it.swabIncidencesDao()
                val ordenDao = it.swabReportDao()
                val anexoKDao = it.daoSwabTipsAnalisisRiesgo()

                val idPozo = swabIncidencesEntity.idpozo
                val idSwabReport = swabIncidencesEntity.idSwabReporte
                val idEstadoSwab = ordenDao.getById(idSwabReport).idEstadoSwab

                Log.e("SERGIO SAVE INCIDENCIA VM", "idPozo = $idPozo / idSwabReport = $idSwabReport")
                Log.e("SERGIO SAVE INCIDENCIA VM", "datosCompletos = $datosCompletos")
                val arrContar = incidencesDao.getAllByIdPozo(idPozo, idSwabReport)
                var contar = arrContar.count()

                if (contar>0) {
                    idRegistroEncontrado = arrContar[0].idregistro
                    if (idRegistroEncontrado ==swabIncidencesEntity.idregistro )
                        esRescribible = true
                    else
                        esRescribible = false
                }
                Log.e("SERGIO", "Contar = $contar")
                Log.e("SERGIO Contar=$contar --> ", arrContar.toString())
                Log.e("SERGIO", "esRescribible = $esRescribible")

                if (contar==1 && esRescribible) {
                    Log.e("SERGIO SAVE INCIDENCIA VM", "##### Reescribir registro ")
                    Log.e("SERGIO SAVE INCIDENCIA VM", "##### IdRegistro="+swabIncidencesEntity.idregistro)
                    Log.e("SERGIO SAVE INCIDENCIA VM", "##### Registro $arrContar")
                    Log.e("SERGIO SAVE INCIDENCIA VM", "##### idEstadoSwab = $idEstadoSwab")
                    Log.e("SERGIO SAVE INCIDENCIA VM", "##### estadoIncidencia = "+swabIncidencesEntity.estadoIncidencia)

                    incidencesDao.insert(swabIncidencesEntity)

                    if (swabIncidencesEntity.estadoIncidencia==1 && idEstadoSwab==4) // datos completos
                        ordenDao.updateEstadoProgreso(swabIncidencesEntity.idSwabReporte)
                    else if (idEstadoSwab==4) // Ya estaba en estado En progreso == 4
                        ordenDao.updateEstadoProgreso(swabIncidencesEntity.idSwabReporte)
                    else
                        ordenDao.updateEstadoEnCurso(swabIncidencesEntity.idSwabReporte)

                    Log.e("SERGIO SAVE INCIDENCIA VM", "swabIncidencesEntity=$swabIncidencesEntity")

                    Log.e("SERGIO SAVE INCIDENCIA VM", "Borrado de Anexo K asociado")

                    // Borrar anexo K Asociado
                    val idRegistroIncidencia = swabIncidencesEntity.idregistro
                    anexoKDao.deleteById(idRegistroIncidencia)

                    _saveSuccess.postValue("Inspección guardada con éxito")

                }
                else if (contar==0 ) {
                    // Primer registro de incidencia en la App
                    Log.e("SERGIO SAVE INCIDENCIA VM", "##### Ingresar primer registro $arrContar")
                    incidencesDao.insert(swabIncidencesEntity)

                    if (swabIncidencesEntity.estadoIncidencia==1) // datos completos
                    {
                        Log.e("SERGIO SAVE INCIDENCIA VM", "-- updateEstadoProgreso")
                        ordenDao.updateEstadoProgreso(swabIncidencesEntity.idSwabReporte)
                    }
                    else {
                        Log.e("SERGIO SAVE INCIDENCIA VM", "-- updateEstadoEnCurso")
                        ordenDao.updateEstadoEnCurso(swabIncidencesEntity.idSwabReporte)

                        // Borrar anexo K Asociado
                        Log.e("SERGIO SAVE INCIDENCIA VM", "Borrado de Anexo K asociado")
                        val idRegistroIncidencia = swabIncidencesEntity.idregistro
                        anexoKDao.deleteById(idRegistroIncidencia)

                    }

                    Log.e("SERGIO SAVE INCIDENCIA VM", "swabIncidencesEntity=$swabIncidencesEntity")
                    Log.e("SERGIO SAVE INCIDENCIA VM", "Inspección guardada Ok")
                    _saveSuccess.postValue("Inspección guardada con éxito")
                } else { // Pozo repetido
                    _saveError.postValue(
                        "Pozo repetido, no puede volver a registrarlo.\n" +
                                "Verifique el POZO e intente nuevamente."
                    )
                }
                var swabReporteTestEntity = ordenDao.getById(swabIncidencesEntity.idSwabReporte)

                Log.e("SERGIO SAVE INCIDENCIA VM", "SwabEntity=$swabReporteTestEntity")
            }
    }

    @SuppressLint("CheckResult")
    fun getIncidence(idRegistro: Int) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.e("SERGIO", "-- CheckResult en IncidenceDetailVM")
                val incidencesDao = it.swabIncidencesDao()
                _getIncidenceSuccess.postValue(incidencesDao.getByIdRegistro(idRegistro))
            }
    }

    /* SERGIO - POZOS */
    @SuppressLint("CheckResult")
    fun getPozos() {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                /*val pozosDao1 = it.getPozosDao()
                _getPozosSucess.postValue(pozosDao.getAll())
                */
                val pozosDao = it.getPozosDao()
                val list = ArrayList(pozosDao.getAll())
                //println("SERGIO DAO = "+list)
                _getPozosSucess.postValue(ArrayList(list))


            }
    }


}