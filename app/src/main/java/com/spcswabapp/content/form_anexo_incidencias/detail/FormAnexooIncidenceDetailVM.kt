package com.spcswabapp.content.form_anexo_incidencias.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.FormAnexooIncidencesEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FormAnexooIncidenceDetailVM(private val db: DatabaseMain) : ViewModel() {

    private val _deleteSuccess = MutableLiveData<String>()
    val deleteSuccess : LiveData<String> get() = _deleteSuccess

    private val _getIncidenceSuccess = MutableLiveData<FormAnexooIncidencesEntity>()
    val getIncidenceSuccess : LiveData<FormAnexooIncidencesEntity> get() = _getIncidenceSuccess

    private val _saveSuccess = MutableLiveData<String>()
    val saveSuccess : LiveData<String> get() = _saveSuccess

    @SuppressLint("CheckResult")
    fun deleteIncidence(idRegistro: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val formAnexooIncidenciasDao = it.formAnexooIncidenciasDao()
                formAnexooIncidenciasDao.deleteById(idRegistro)
                _deleteSuccess.postValue("Registro eliminado.")
            }
    }
    @SuppressLint("CheckResult")
    fun saveIncidence(formAnexooIncidencesEntity: FormAnexooIncidencesEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val formAnexooIncidenciasDao = it.formAnexooIncidenciasDao()
                formAnexooIncidenciasDao.insert(formAnexooIncidencesEntity)
                _saveSuccess.postValue("Registro guardado con éxito")
            }
    }

    @SuppressLint("CheckResult")
    fun getIncidence(idRegistro: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val formAnexooIncidenciasDao = it.formAnexooIncidenciasDao()
                _getIncidenceSuccess.postValue(formAnexooIncidenciasDao.getByIdRegistro(idRegistro))
            }
    }

}