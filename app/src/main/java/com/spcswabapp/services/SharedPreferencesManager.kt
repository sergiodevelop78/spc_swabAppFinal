package com.spcswabapp.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spcswabapp.BuildConfig
import com.spcswabapp.content.new_anexok.entities.TipsAnalisisRiesgoEntity
import com.spcswabapp.database.entities.PozosEntity
import com.spcswabapp.models.CriticalPoint
import com.spcswabapp.models.LoginData
import com.spcswabapp.models.RevisionesDiarias

class SharedPreferencesManager(var context: Context) {

    companion object {
        const val KEYAPP = BuildConfig.APPLICATION_ID
        private var newInstance: SharedPreferences? = null
        const val USERKEY = "userData"
        private const val CRITICALPOINTLISTKEY = "criticalPointList"
        private const val REVISIONESDIARIASLISTKEY = "revisionesDiariasList"
        private const val TIPSANALISISRIESGOLISTKEY = "tipsAnalisisRiesgoList"
        private const val POZOSLISTKEY = "pozosList"

        private fun getInstance(context: Context): SharedPreferences {
            if (newInstance == null) {
                newInstance = context.getSharedPreferences(KEYAPP, Context.MODE_PRIVATE)
            }
            return newInstance!!
        }
    }

    fun saveUserData(loginData: LoginData) {
        val jsonData = Gson().toJson(loginData)
        getInstance(context).edit().apply {
            putString(USERKEY, jsonData)
        }.apply()
    }

    fun getCriticalPointList(): ArrayList<CriticalPoint>? {
        val jsonString = getInstance(context).getString(CRITICALPOINTLISTKEY, "")
        val type = object : TypeToken<ArrayList<CriticalPoint>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    fun saveCriticalPointList(criticalPointList: ArrayList<CriticalPoint>) {
        val jsonData = Gson().toJson(criticalPointList)
        getInstance(context).edit().apply {
            putString(CRITICALPOINTLISTKEY, jsonData)
        }.apply()
    }

    fun getUserData(): LoginData? {
        val jsonString = getInstance(context).getString(USERKEY, "")
        //Log.d("SERGIO", "getUserData=" + jsonString)
        return Gson().fromJson(jsonString, LoginData::class.java) ?: null
    }

    /* SERGIO ANEXO O - REVISIONES DIARIAS*/
    fun getRevisionesDiariasList(): ArrayList<RevisionesDiarias>? {
        val jsonString = getInstance(context).getString(REVISIONESDIARIASLISTKEY, "")
        //Log.d("SERGIO", "getRevisionesDiariasList=" + jsonString)
        val type = object : TypeToken<ArrayList<RevisionesDiarias>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    fun saveRevisionesDiariasList(revisionesDiariasList: ArrayList<RevisionesDiarias>) {
        val jsonData = Gson().toJson(revisionesDiariasList)
        //Log.d("SERGIO", "saveRevisionesDiariasList=" + jsonData)
        getInstance(context).edit().apply {
            putString(REVISIONESDIARIASLISTKEY, jsonData)
        }.apply()
    }

    /* SERGIO ANEXO K - TIPS ANALISIS DE RIESGO */
    fun getTipsAnalisisRiesgoList(): ArrayList<TipsAnalisisRiesgoEntity>? {
        val jsonString = getInstance(context).getString(TIPSANALISISRIESGOLISTKEY, "")
        //Log.d("SERGIO", "getTipsAnalisisRiesgoList=" + jsonString)
        val type = object : TypeToken<ArrayList<TipsAnalisisRiesgoEntity>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    fun saveTipsAnalisisRiesgoList(tipsAnalisisRiesgoList: ArrayList<TipsAnalisisRiesgoEntity>) {
        val jsonData = Gson().toJson(tipsAnalisisRiesgoList)
        //Log.d("SERGIO ", "saveTipsAnalisisRiesgoList=" + jsonData)
        getInstance(context).edit().apply {
            putString(TIPSANALISISRIESGOLISTKEY, jsonData)
        }.apply()
    }
    /* FIN ANEXO K - TIPS ANALISIS DE RIESGO */


    /* SERGIO POZOS */
    fun getPozosList(): ArrayList<PozosEntity>? {
        val jsonString = getInstance(context).getString(POZOSLISTKEY, "")
        //Log.d("SERGIO", "getPozosList=" + jsonString)
        val type = object : TypeToken<ArrayList<PozosEntity>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
    fun savePozosList(pozosList : ArrayList<PozosEntity>){
        val jsonData = Gson().toJson(pozosList)
        //Log.d("SERGIO", "savePozosList="+jsonData)
        getInstance(context).edit().apply {
            putString(POZOSLISTKEY, jsonData)
        }.apply()
    }
    /* FIN POZOS */


}