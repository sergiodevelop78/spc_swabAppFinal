package com.spcswabapp.content.form_anexo_incidencias

import android.view.LayoutInflater
import android.view.View
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.form_anexo_incidencias.adapter.FormAnexooIncidenceAdapter
import com.spcswabapp.content.form_anexo_incidencias.detail.FormAnexooIncidenceDetailActivity
import com.spcswabapp.database.entities.FormAnexooIncidencesEntity
import com.spcswabapp.databinding.ActivityIncidence2Binding
import com.spcswabapp.utils.openActivity
import com.spcswabapp.utils.showMessage
import org.koin.android.ext.android.inject

class FormAnexooIncidenceActivity : ActivityViewBinding<ActivityIncidence2Binding,FormAnexooIncidenceVM>() {

    private val adapter : FormAnexooIncidenceAdapter by inject()

    override fun inflateLayout(layoutInflater: LayoutInflater) = ActivityIncidence2Binding.inflate(layoutInflater)
    private var isComplete = false

    override fun viewListeners() {
        val idFormAnexoo = intent.getIntExtra("idFormAnexoo",-1)
        binding.apply {
            rvIncidences.adapter = adapter

            adapter.onItemClickListenerPos = {it , pos ->
                openActivity(FormAnexooIncidenceDetailActivity::class.java, extras = {
                    putInt("idRegistro",it.idRegistro)
                    putBoolean("isComplete",isComplete)
                    putString("title","Registro #${pos + 1}")
                })
            }
            imgAddIncidence.setOnClickListener {
                alertDialog.message = "¿Esta seguro que desea agregar un nuevo reporte?"
                alertDialog.onAcceptClickListener = {
                    val formAnexooIncidencesEntity = FormAnexooIncidencesEntity(
                        idFormAnexoo = idFormAnexoo,
                        kmInicial = "",
                        kmFinal = "",
                        horometroInicial = "",
                        horometroFinal = "",
                        equipo = "",
                        origen = "",
                        destino = "",
                        horaInicial = "",
                        horaFinal = "",
                        oil = "",
                        agua = "",
                        s_t = "",
                        npcRoto  = "",
                        npcNuevo = "",
                        npeRoto = "",
                        npeNuevo = "",
                        tanque_descarga_zona = "",
                        observaciones = ""
                    )
                    viewModel.addIncidence(formAnexooIncidencesEntity)
                }
                alertDialog.show()
            }
        }
        viewModel.getIncidences(idFormAnexoo)
    }

    override fun onResume() {
        super.onResume()
        val idFormAnexoo = intent.getIntExtra("idFormAnexoo",-1)
        viewModel.getIncidences(idFormAnexoo)
    }

    override fun viewModelListeners() {
        viewModel.apply {
            getStadoSwab.observe(this@FormAnexooIncidenceActivity){
                isComplete = it
                binding.imgAddIncidence.visibility = if(it) View.GONE else View.VISIBLE
            }
            getIncidencesSuccess.observe(this@FormAnexooIncidenceActivity){
                adapter.list = it
                binding.txtMessage.visibility =  if (it.count() > 0) View.GONE else View.VISIBLE
            }

            addIncidenceSuccess.observe(this@FormAnexooIncidenceActivity){
                viewModel.getIncidences(intent.getIntExtra("idFormAnexoo",-1))
                showMessage(it)
            }

        }
    }

}