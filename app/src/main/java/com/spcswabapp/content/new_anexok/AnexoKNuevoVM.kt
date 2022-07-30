package com.spcswabapp.content.new_anexok

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.database.entities.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

@Suppress("SpellCheckingInspection")
class AnexoKNuevoVM(private val db: DatabaseMain) : ViewModel() {

    private val _deleteSuccess = MutableLiveData<String>()
    val deleteSuccess : LiveData<String> get() = _deleteSuccess

    private val _getIncidenceSuccess = MutableLiveData<SwabIncidencesEntity>()
    val getIncidenceSuccess : LiveData<SwabIncidencesEntity> get() = _getIncidenceSuccess

    private val _saveSuccess = MutableLiveData<String>()
    val saveSuccess : LiveData<String> get() = _saveSuccess

    private val _getSwabAnexoKItemsSucess = MutableLiveData<ArrayList<SwabCriticalPointEntity>>()
    val getSwabAnexoKItemsSucess: LiveData<ArrayList<SwabCriticalPointEntity>> get() = _getSwabAnexoKItemsSucess


    @SuppressLint("CheckResult")
    fun deleteIncidence(idRegistro: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.swabIncidencesDao()
                incidencesDao.deleteById(idRegistro)
                _deleteSuccess.postValue("Inspección eliminada")
            }
    }
    @SuppressLint("CheckResult")
    fun saveIncidence(swabIncidencesEntity: SwabIncidencesEntity){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.swabIncidencesDao()
                incidencesDao.insert(swabIncidencesEntity)
                _saveSuccess.postValue("Inspección guardada con éxito")
            }
    }

    @SuppressLint("CheckResult")
    fun getIncidence(idRegistro: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe {
                val incidencesDao = it.swabIncidencesDao()
                _getIncidenceSuccess.postValue(incidencesDao.getByIdRegistro(idRegistro))
            }
    }


    @SuppressLint("CheckResult")
    fun getSwabAnexoKItems(idSwabReport: Int){
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe ({
                val criticalPointSwab = it.swabCriticalPointDao()
                val list = ArrayList(criticalPointSwab.getSwabCriticalPointById(idSwabReport))
                _getSwabAnexoKItemsSucess.postValue(ArrayList(list))
            },{
                it.printStackTrace()
            })
    }


}