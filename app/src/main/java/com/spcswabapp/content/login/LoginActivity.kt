package com.spcswabapp.content.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import com.spcswabapp.BuildConfig
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.home.HomeActivity
import com.spcswabapp.content.home2.Home2Activity
import com.spcswabapp.databinding.ActivityLoginBinding
import com.spcswabapp.utils.hideKeyboard
import com.spcswabapp.utils.openActivity
import com.spcswabapp.utils.showMessage
import java.util.logging.Handler

@Suppress("SpellCheckingInspection")
private const val VERSION_COMPILACION = "1.0.b5 - 20220622"

class LoginActivity : ActivityViewBinding<ActivityLoginBinding, LoginVM>() {

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityLoginBinding.inflate(layoutInflater)

    private var count = 0

    var intentX = Intent()


    override fun viewListeners() {
        val versionName = BuildConfig.VERSION_NAME

        binding.etVersion.setText("$versionName")

        ActivityLoginBinding.inflate(layoutInflater)

        binding.apply {
            btnLogin.setOnClickListener {
                hideKeyboard()
                progressDialog.message = "Iniciando sesión"
                progressDialog.show()
                viewModel.login(etUsername.text.toString(), etPassword.text.toString())
            }
        }
    }

    override fun viewModelListeners() {
        viewModel.apply {
            loginSuccess.observe(this@LoginActivity) {
                val data = sharedPreferencesManager.getUserData()
                val txtIdUsuario = data?.idUsuario
                val txtAppModulo = data?.appModulo
                //Log.d("SERGIO", ">>>> IDUSUARIO = $txtIdUsuario")
                //Log.d("SERGIO", ">>>> XAPPMODULO = $txtAppModulo")
                count++
                if(count > 1){

                    if (txtAppModulo=="1") {
                        //progressDialog.hide()
                        openActivity(HomeActivity::class.java, delay=1000, finishExisting = true, extras =  {FLAG_ACTIVITY_CLEAR_TOP} )
                        count = 0
                        Log.d("SERGIO", "### PID_modulo $txtAppModulo")
                    }
                    else  if (txtAppModulo=="2"){
                        openActivity(Home2Activity::class.java, finishExisting = true)
                        count = 0
                        Log.d("SERGIO", "### RANDU-->idapp_modulo $txtAppModulo")

                    }
                    else  {
                        openActivity(HomeActivity::class.java, finishExisting = true)
                        count = 0
                        Log.d("SERGIO", "### RANDU-->idapp_modulo $txtAppModulo")

                    }
                }
            }

            loginError.observe(this@LoginActivity) {
                progressDialog.hide()
                showMessage(it)
                count=0
            }
        }
    }

}