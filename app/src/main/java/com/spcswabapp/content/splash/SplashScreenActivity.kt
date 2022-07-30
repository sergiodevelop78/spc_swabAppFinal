package com.spcswabapp.content.splash

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.home.HomeActivity
import com.spcswabapp.content.home2.Home2Activity
import com.spcswabapp.content.login.LoginActivity
import com.spcswabapp.databinding.ActivitySplashScreenBinding
import com.spcswabapp.utils.openActivity

@SuppressLint("CustomSplashScreen")

class SplashScreenActivity : ActivityViewBinding<ActivitySplashScreenBinding, SplashScreenVM>() {
    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun viewListeners() {
        viewModel.checkIfLogin()

    }

    override fun viewModelListeners() {
        viewModel.checkLoginResult.observe(this){
            val idusuario = this.viewModel.preferencesManager.getUserData()?.idUsuario
            val idappModulo  = this.viewModel.preferencesManager.getUserData()?.appModulo

            Log.d("SERGIO viewModelListeners", "++++ IdUsuario = $idusuario")
            Log.d("SERGIO viewModelListeners", "++++ IDAPP_modulo = $idappModulo")

            if (idappModulo=="1") {
                openActivity(
                    if (it)
                        LoginActivity::class.java
                    else
                        HomeActivity::class.java,

                    delay = 1500, finishExisting = true
                )
            }
            else if (idappModulo=="2") {
                openActivity(
                    if (it)
                        LoginActivity::class.java
                    else
                        Home2Activity::class.java,

                    delay = 1500, finishExisting = true
                )
            }
            else  {
                openActivity(
                    if (it)
                        LoginActivity::class.java
                    else
                        Home2Activity::class.java,

                    delay = 1500, finishExisting = true
                )
            }
        }
    }

}