package com.spcswabapp.content.form_anexo_incidencias

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.FormAnexooIncidencesEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FormAnexooIncidenceVM(private val db: DatabaseMain) : ViewModel() {

    private val _getIncidencesSuccess = MutableLiveData<ArrayList<FormAnexooIncidencesEntity>>()
    val getIncidencesSuccess : LiveData<ArrayList<FormAnexooIncidencesEntity>> get() = _getIncidencesSuccess

    private val _addIncidenceSuccess = MutableLiveData<String>()
    val addIncidenceSuccess : LiveData<String> get() = _addIncidenceSuccess

    private val _getStadoSwab = MutableLiveData<Boolean>()
    val getStadoSwab : LiveData<Boolean> get() = _getStadoSwab

    @SuppressLint("CheckResult")
    fun getIncidences(idFormAnexoo: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val formAnexooIncidenciasDao = it.formAnexooIncidenciasDao()
                val formAnexooReportDao = it.formAnexooReportDao()
                val report = formAnexooReportDao.getById(idFormAnexoo)
                val list = ArrayList(formAnexooIncidenciasDao.getAllById(idFormAnexoo))

                if(list.size > 0 && report.idEstado != 3){
                    report.idEstado = 2
                }
                else if(list.size == 0 && report.idEstado != 3){
                    report.idEstado = 1
                }
                formAnexooReportDao.insertUser(report)
                if(report.idEstado == 3){
                    _getStadoSwab.postValue(true)
                }else{
                    _getStadoSwab.postValue(false)
                }

                _getIncidencesSuccess.postValue(list)
            }
    }

    @SuppressLint("CheckResult")
    fun addIncidence(formAnexooIncidencesEntity: FormAnexooIncidencesEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.formAnexooIncidenciasDao()
                incidencesDao.insert(formAnexooIncidencesEntity)
                _addIncidenceSuccess.postValue("Registro agregado con éxito")
            }
    }


}