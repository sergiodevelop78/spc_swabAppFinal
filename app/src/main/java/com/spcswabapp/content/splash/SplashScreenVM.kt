package com.spcswabapp.content.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spcswabapp.services.SharedPreferencesManager

class SplashScreenVM(val preferencesManager: SharedPreferencesManager) : ViewModel() {

    private val _checkLoginResult = MutableLiveData<Boolean>()
    val checkLoginResult: LiveData<Boolean> get() = _checkLoginResult


    fun checkIfLogin(){
        _checkLoginResult.value = (preferencesManager.getUserData() == null && preferencesManager.getCriticalPointList() == null)
    }

}