package com.spcswabapp.content.new_order

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.spcswabapp.R
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.new_order.adapter.ViewPagerAdapter
import com.spcswabapp.content.new_order.fragments.*
import com.spcswabapp.database.entities.SwabReportEntity
import com.spcswabapp.databinding.ActivityNewOrderBinding
import com.spcswabapp.utils.hideKeyboard
import com.spcswabapp.utils.showMessage
import com.spcswabapp.sergiofunciones.SergioFunciones

class NewOrderActivity : ActivityViewBinding<ActivityNewOrderBinding, NewOrderVM>() {

    private val titleCategory = arrayOf(
        "Datos de Registro",
        "Combustible",
        "Verificacion de Puntos Críticos",
        "Materiales de Consumo Diario",
        "Despacho de Cisternas"
    )

    lateinit var swabReportEntity: SwabReportEntity
    private var isExist = false
    private var fragmentsAllLlenos = false
    private var fragmentsRegisterLleno = false

    override fun onBackPressed() {
        //Toast.makeText(this, "ATRAS", Toast.LENGTH_SHORT).show()
        if (swabReportEntity.idEstadoSwab != 3) {
            showAlertDialogo(
                "Error",
                "No puede salir sin haber registrado datos.\nSi quiere regresar a la ventana anterior, elimine esta orden."
            )
        }
        else
            super.onBackPressed()

        //startActivity(Intent(this, NewOrderActivity::class.java))
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityNewOrderBinding.inflate(layoutInflater)

    override fun viewListeners() {
        progressDialog.message = "Obteniendo datos..."
        binding.viewPager.offscreenPageLimit = 5
        isExist = intent.getBooleanExtra("isExist", false)
        val title = "Editar " + intent.getStringExtra("title")

        if (isExist) binding.toolbar.title = title

        val idSwabReport = intent.getIntExtra("idSwabReport", -1)
        viewModel.createNewSwabReport(isExist, idSwabReport)
        progressDialog.show()

        binding.imgLlenar.setOnClickListener {
            for (fragment in supportFragmentManager.fragments) {
                when (fragment) {
                    is RegisterDataFragment -> {
                        fragment.llenarCamposRandom()
                        fragmentsRegisterLleno = true
                    }
                    is FuelFragment -> {
                        fragment.llenarCamposRandom()
                    }
                    is FuelFragment -> {
                        fragment.llenarCamposRandom()
                    }
                    is DayliConsumptionMaterialsFragment -> {
                        fragment.llenarCamposRandom()
                    }
                    is TankDispatchFragment -> {
                        fragment.llenarCamposRandom()
                    }

                }
            }
            //fragmentsAllLlenos = true
        }

        binding.imgSave.setOnClickListener {

            fragmentsRegisterLleno = validarCampos()

            /*if (!fragmentsAllLlenos) {
                showAlertDialogo("Atención", "Debe llenar todas las pantallas y campos.")
             */
            if (!fragmentsRegisterLleno){
                showAlertDialogo("Atención", "Debe llenar los campos del registro principal antes de guardar.")
            }
            else {
                alertDialog.message = "¿Esta seguro que desea guardar los cambios?"
                alertDialog.isDelete = false
                alertDialog.onAcceptClickListener = {
                    hideKeyboard()
                    progressDialog.message = "Guardando datos..."
                    progressDialog.show()
                    for (fragment in supportFragmentManager.fragments) {
                        when (fragment) {
                            is RegisterDataFragment -> {
                                fragment.saveData()
                            }
                            is FuelFragment -> {
                                fragment.saveData()
                            }
                            is CriticalPointVerificationFragment -> {
                                fragment.saveData()
                            }
                            is DayliConsumptionMaterialsFragment -> {
                                fragment.saveData()
                            }
                            is TankDispatchFragment -> {
                                fragment.saveData()
                            }
                        }
                    }
                    Handler(Looper.myLooper()!!).postDelayed({
                        showMessage("Datos guardados correctamente")
                        progressDialog.hide()
                        this.finish()
                    }, 1500)
                }
                alertDialog.show()
            }
        }

        binding.imgDelete.setOnClickListener {
            alertDialog.message = "¿Está seguro que desea eliminar este reporte?"
            alertDialog.isDelete = true
            alertDialog.onAcceptClickListener = {
                progressDialog.message = "Eliminando reporte..."
                progressDialog.show()
                viewModel.deleteSwabReport(swabReportEntity.idSwabReporte)
            }
            alertDialog.show()
        }

    }

    private fun validarCampos(): Boolean {
        var respuesta = false
        for (fragment in supportFragmentManager.fragments) {
            when (fragment) {
                is RegisterDataFragment -> {
                    if (fragment.validarCampos())
                        respuesta = true
                }
            }
        }
        return respuesta

    }


    private fun showAlertDialogo(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            // no hacer nada
        }
        builder.show()
    }

    private fun initViewPager() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = titleCategory[position]
            }.attach()
        }
        Handler(Looper.myLooper()!!).postDelayed({
            progressDialog.hide()
        }, 250)
    }


    private fun checkStatus() {
        binding.apply {
            if (swabReportEntity.idEstadoSwab == 3) {

                imgSave.visibility = View.GONE
                imgDelete.visibility = View.GONE
                /* Sergio Temporal*/
                imgLlenar.visibility = View.GONE
            } else {
                imgSave.visibility = View.VISIBLE
                imgDelete.visibility = View.VISIBLE
                /* Sergio Temporal*/
                //imgLlenar.visibility = View.VISIBLE
                imgLlenar.visibility = View.GONE
            }
        }


    }

    override fun viewModelListeners() {
        viewModel.apply {
            createSwabReportSuccess.observe(this@NewOrderActivity) {
                swabReportEntity = it
                checkStatus()
                initViewPager()
            }

            saveChangesSuccess.observe(this@NewOrderActivity) {
                progressDialog.hide()
                showMessage(it)
            }

            onDeleteSuccess.observe(this@NewOrderActivity) {
                progressDialog.hide()
                showMessage(it)
                finish()
            }
        }
    }
}


interface IOnBackPressed {
    fun onBackPressed(): Boolean
}
