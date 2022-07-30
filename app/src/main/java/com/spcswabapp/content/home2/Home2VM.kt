package com.spcswabapp.content.home2

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.FormAnexooReportEntity
import com.spcswabapp.services.RetrofitApi
import com.spcswabapp.services.RetrofitConnection
import com.spcswabapp.services.SharedPreferencesManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class Home2VM(
    val db: DatabaseMain,
    private val rc: RetrofitConnection,
    private val api: RetrofitApi,
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _getFormAnexooSuccess = MutableLiveData<ArrayList<FormAnexooReportEntity>>()
    val getFormAnexooSuccess: LiveData<ArrayList<FormAnexooReportEntity>> get() = _getFormAnexooSuccess

    private val _onDeleteFormAnexooSuccess = MutableLiveData<String>()
    val onDeleteFormAnexooSuccess: LiveData<String> get() = _onDeleteFormAnexooSuccess


    @SuppressLint("CheckResult")
    fun getOrder() {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val orderDao = it.formAnexooReportDao()
                val list = ArrayList(orderDao.getAll())
                _getFormAnexooSuccess.postValue(list)
            }, {
                print(it)
            })
    }

    var count = 0

    @SuppressLint("CheckResult")
    fun syncrhonize() {
        var count = 0
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val orderDao = it.formAnexooReportDao()
                val list = ArrayList(orderDao.getAllSync())
                if (list.count() <= 0) {
                    _onDeleteFormAnexooSuccess.postValue("No existen ordenes pendientes")
                    return@subscribe
                }

                list.map { formAnexooReportEntity ->
                    Log.d("SERGIO", "### INICIO SYNC")
                    val data = HashMap<String,Any>()

                    val inspeccionList = it.formAnexooIncidenciasDao().getAllById(formAnexooReportEntity.idFormAnexoo)

                    val revisionesDiariasList = it.formAnexooRevisionesDiariasDao().getFormAnexooRevisionesDiariasById(
                        formAnexooReportEntity.idFormAnexoo
                    )

                    Log.d("SERGIO", ">>> ANTES Update FormReportEntity "+formAnexooReportEntity.toString())

                    data["data-formAnexoO"] = arrayListOf(formAnexooReportEntity)
                    data["data-formAnexoO_registros"] = inspeccionList

                    Log.d("SERGIO", data.toString())
                    //val pc = HashMap<String,Any>()

                    data["data-formAnexoO_revisionesDiarias"] = revisionesDiariasList
                    //pc["pcs"] = revisionesDiariasList
                    //data["data-formAnexoO_revisionesDiarias"] = arrayListOf(pc)

                    Log.d("SERGIO", ">>> ANTES Update FormReportEntity "+formAnexooReportEntity.toString())

                    rc.connection(api.uploadDetailsFormAnexoo(data = data), success = {

                        if (it != null) {
                            if (it.error) {
                                Log.d("SERGIO", "### ERROR API " + it.message)
                                count++
                                check(list.size,count)
                                _onDeleteFormAnexooSuccess.postValue(it.message)
                            }
                            else {
                                /* SERGIO */
                                Thread {
                                    var idNewId = it.data.toString()
                                    formAnexooReportEntity.idFormAnexoProduccion = idNewId
                                    orderDao.updateIDProduccion(
                                        formAnexooReportEntity.idFormAnexoo,
                                        idNewId
                                    )
                                }.start()

                                //Log.d("SERGIO", ">>> After UPDATE FormReportEntity: "+formAnexooReportEntity.toString())
                                Log.d("SERGIO", "idFormAnexoProduccion = " + formAnexooReportEntity.idFormAnexoProduccion)
                                if(!it!!.error){
                                    /* SERGIO: Si el estado es 3 (Finalizado), borrar FORM de celular */
                                    if (formAnexooReportEntity.idEstado==3) {
                                        deleteSwabReport(formAnexooReportEntity.idFormAnexoo)
                                    }
                                }
                                count++
                                check(list.size,count)
                            }
                        }
                    }, error = {
                        count++
                        check(list.size,count)
                        _onDeleteFormAnexooSuccess.postValue(it)

                    })
                }

            }
    }

    fun check(size: Int, count: Int){
        if(count == size){
            _onDeleteFormAnexooSuccess.postValue("Form Anexo O sincronizado con exito")
        }
    }

    @SuppressLint("CheckResult")
    fun completeReport(formAnexooReport: FormAnexooReportEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val formAnexooReportDao = it.formAnexooReportDao()
                formAnexooReport.idEstado = 3
                formAnexooReportDao.insertUser(formAnexooReport)
                _onDeleteFormAnexooSuccess.postValue("Form Anexo O finalizado con exito")
                getOrder()
            }
    }



    @SuppressLint("CheckResult")
    fun deleteSwabReport(idFormAnexo: Int) {
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val reportDao = it.formAnexooReportDao()
                val swabCriticalPointDao = it.formAnexooRevisionesDiariasDao()
                swabCriticalPointDao.deleteFormAnexooRevisionesDiarias(idFormAnexo)
                reportDao.deleteSwabReport(idFormAnexo)
                //swabIncidencesDao.deleteById(idFormAnexo)
                getOrder()
                // _onDeleteSuccess.postValue("Swab sincronizado con exito")
            }
    }


}