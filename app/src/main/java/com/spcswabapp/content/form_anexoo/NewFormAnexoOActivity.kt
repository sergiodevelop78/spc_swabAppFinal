package com.spcswabapp.content.form_anexoo

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.form_anexoo.adapter.ViewPagerAdapter
import com.spcswabapp.content.form_anexoo.fragments.*
import com.spcswabapp.database.entities.FormAnexooReportEntity
import com.spcswabapp.databinding.ActivityNewFormAnexooBinding
import com.spcswabapp.utils.hideKeyboard
import com.spcswabapp.utils.showMessage

class NewFormAnexoOActivity : ActivityViewBinding<ActivityNewFormAnexooBinding, NewFormAnexoOVM>() {

    private val titleCategory = arrayOf(
        "Datos de Registro",
        "Revisiones Diarias",
    )

    lateinit var formAnexooReportEntity: FormAnexooReportEntity
    private var isExist = false

    private var fragmentsRegisterLleno = false


    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityNewFormAnexooBinding.inflate(layoutInflater)

    override fun viewListeners() {
        progressDialog.message = "Obteniendo datos..."

        //Log.d("SERGIO 1", "#### viewListeners")

        binding.viewPager.offscreenPageLimit = 2
        isExist = intent.getBooleanExtra("isExist", false)
        val title = "Editar " + intent.getStringExtra("title")

        //Log.d("SERGIO 1.2", "#### "+title)
        if (isExist) binding.toolbar.title = title

        val idFormAnexoo = intent.getIntExtra("idFormAnexoo", -1)
        //Log.d("SERGIO 1.3", "idFormAnexoo = "+idFormAnexoo)
        viewModel.createNewFormAnexooReport(isExist, idFormAnexoo)
        progressDialog.show()

        binding.imgSave.setOnClickListener {
            alertDialog.message = "¿Esta seguro que desea guardar los cambios?"
            alertDialog.isDelete = false
            alertDialog.onAcceptClickListener = {
                hideKeyboard()
                progressDialog.message = "Guardando datos..."
                progressDialog.show()
                for (fragment in supportFragmentManager.fragments) {
                    when (fragment) {
                        is FormAnexoORegisterDataFragment -> {
                            fragment.saveData()

                        }
                        is RevisionesDiariasFragment -> {
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

        binding.imgDelete.setOnClickListener {
            alertDialog.message = "¿Está seguro que desea eliminar este reporte?"
            alertDialog.isDelete = true
            alertDialog.onAcceptClickListener = {
                progressDialog.message = "Eliminando reporte..."
                progressDialog.show()
                viewModel.deleteSwabReport(formAnexooReportEntity.idFormAnexoo)
            }
            alertDialog.show()
        }

        /* Sergio Temporal para pruebas */
        binding.imgLlenar.setOnClickListener {
            for (fragment in supportFragmentManager.fragments) {
                when (fragment) {
                    is FormAnexoORegisterDataFragment -> {
                        fragment.llenarCamposRandom()
                        fragmentsRegisterLleno = true
                    }
                    is RevisionesDiariasFragment -> {
                        fragment.llenarCamposRandom()
                    }
                }
            }
            //fragmentsAllLlenos = true
        }

    }

    private fun initViewPager() {
        //Log.d("SERGIO", "#### initViewPager")
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
            if (formAnexooReportEntity.idEstado == 3) {
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
            createFormAnexooReportSuccess.observe(this@NewFormAnexoOActivity) {

                formAnexooReportEntity = it
                checkStatus()
                initViewPager()
            }

            saveChangesSuccess.observe(this@NewFormAnexoOActivity) {
                progressDialog.hide()
                showMessage(it)
            }

            onDeleteSuccess.observe(this@NewFormAnexoOActivity) {
                progressDialog.hide()
                showMessage(it)
                finish()
            }
        }
    }


}