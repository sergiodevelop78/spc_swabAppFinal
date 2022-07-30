package com.spcswabapp.content.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.models.LoginData
import com.spcswabapp.services.RetrofitApi
import com.spcswabapp.services.RetrofitConnection
import com.spcswabapp.services.SharedPreferencesManager

class LoginVM(
    private val rc: RetrofitConnection,
    private val api: RetrofitApi,
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _loginSuccess = MutableLiveData<String>()
    val loginSuccess: LiveData<String> get() = _loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> get() = _loginError

    fun login(username: String, password: String) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            _loginError.value = "Ingrese todos los campos"
            return
        }
        val data = HashMap<String, String>()
        data["username"] = username
        data["password"] = password

        rc.connection(api.login(data), success = {
            if (!it!!.error) {
                sharedPreferencesManager.saveUserData(it.data as LoginData)
                _loginSuccess.value = ""

                val data2 = HashMap<String,String>()
                data2["accion"] = "1"
                rc.connection(api.getCriticalPoint(data = data2), success = {
                    if (!it!!.error) {
                        it.data?.let { it1 -> sharedPreferencesManager.saveCriticalPointList(it1) }
                        Log.e("SERGIO", "-- getCriticalPoint data2 --")
                        _loginSuccess.value = ""
                    } else {
                        _loginError.value = it.message
                    }
                }, error = {
                    _loginError.value = it
                })

                /* SERGIO */
                val data3 = HashMap<String,String>()
                data3["accion"] = "1"
                rc.connection(api.getRevisionesDiarias(data = data3), success = {
                    if (!it!!.error) {
                        it.data?.let { it2 -> sharedPreferencesManager.saveRevisionesDiariasList(it2) }
                        Log.e("SERGIO", "-- getCriticalPoint data3 --")
                        //_loginSuccess.value = ""
                    } else {
                        _loginError.value = it.message
                    }
                }, error = {
                    _loginError.value = it
                })

                /* SERGIO TIPS ANALISIS DE RIESGO */
                val data5 = HashMap<String,String>()
                data5["accion"] = "1"
                rc.connection(api.getTipsAnalisisRiesgoRF(data = data5), success = {
                    if (!it!!.error) {
                        it.data?.let { it2 -> sharedPreferencesManager.saveTipsAnalisisRiesgoList(it2) }
                        //_loginSuccess.value = ""
                    } else {
                        _loginError.value = it.message
                    }
                }, error = {
                    _loginError.value = it
                })
                /* FIN SERGIO TIPS ANALISIS DE RIESGO */


                /* SERGIO TABLA POZOS */
                val dataPozos= HashMap<String,String>()
                dataPozos["accion"] = "1"
                rc.connection(api.getTablaPozos(data = dataPozos), success = {
                    if (!it!!.error) {
                        it.data?.let { it2 -> sharedPreferencesManager.savePozosList(it2) }
                        //_loginSuccess.value = ""
                    } else {
                        _loginError.value = it.message
                    }
                }, error = {
                    _loginError.value = it
                })
                /* FIN SERGIO TIPS ANALISIS DE RIESGO */

            } else {
                _loginError.value = it.message
            }
        }, error = {
            _loginError.value = it
        })



    }
}