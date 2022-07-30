package com.spcswabapp.app

import androidx.room.Room
import com.spcswabapp.content.form_anexo_incidencias.FormAnexooIncidenceVM
import com.spcswabapp.content.form_anexo_incidencias.adapter.FormAnexooIncidenceAdapter
import com.spcswabapp.content.form_anexo_incidencias.detail.FormAnexooIncidenceDetailVM
import com.spcswabapp.content.home.adapter.OrderAdapter
import com.spcswabapp.content.home.HomeVM
import com.spcswabapp.content.incidences.IncidenceVM
import com.spcswabapp.content.incidences.detail.IncidenceDetailVM
import com.spcswabapp.content.login.LoginVM
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.content.splash.SplashScreenVM
import com.spcswabapp.database.DatabaseMain
import com.spcswabapp.services.RetrofitClient
import com.spcswabapp.services.RetrofitConnection
import com.spcswabapp.services.SharedPreferencesManager
import com.spcswabapp.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.spcswabapp.content.form_anexoo.NewFormAnexoOVM
import com.spcswabapp.content.home2.Home2VM
import com.spcswabapp.content.home2.adapter.Order2Adapter
import com.spcswabapp.content.incidences.adapter.IncidenceAdapter
import com.spcswabapp.content.new_anexok.NewAnexoKVM


val appModule = module {
    viewModel { SplashScreenVM(get()) }
    viewModel { HomeVM(get(),get(),get(),get()) }
    viewModel { LoginVM(get(),get(),get()) }
    viewModel { NewOrderVM(get(),get()) }
    viewModel { IncidenceVM(get()) }
    viewModel {IncidenceDetailVM(get())}

    viewModel { Home2VM(get(),get(),get(),get()) }
    viewModel { NewFormAnexoOVM(get(),get()) }
    viewModel { FormAnexooIncidenceVM(get()) }
    factory { Order2Adapter() }
    factory { FormAnexooIncidenceAdapter() }
    viewModel { FormAnexooIncidenceDetailVM(get()) }
    viewModel { NewAnexoKVM (get(),get()) }

    factory { OrderAdapter() }

    factory { IncidenceAdapter() }
    single { RetrofitConnection() }
    single { RetrofitClient.createService() }
    single { SharedPreferencesManager(androidContext()) }
    single { Room.databaseBuilder(androidContext(), DatabaseMain::class.java,Constants.DATABASE_NAME).build() }
}