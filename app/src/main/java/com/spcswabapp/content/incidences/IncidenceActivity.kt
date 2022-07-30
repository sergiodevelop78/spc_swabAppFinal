package com.spcswabapp.content.incidences

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.incidences.adapter.IncidenceAdapter
import com.spcswabapp.content.incidences.detail.IncidenceDetailActivity
import com.spcswabapp.content.new_anexok.AnexoKNuevoActivity
import com.spcswabapp.database.entities.SwabIncidencesEntity
import com.spcswabapp.databinding.ActivityIncidenceBinding
import com.spcswabapp.utils.openActivity
import com.spcswabapp.utils.showMessage
import org.koin.android.ext.android.inject

@Suppress("SpellCheckingInspection")
class IncidenceActivity : ActivityViewBinding<ActivityIncidenceBinding,IncidenceVM>() {

    private val adapter : IncidenceAdapter by inject()

    override fun inflateLayout(layoutInflater: LayoutInflater) = ActivityIncidenceBinding.inflate(layoutInflater)
    private var isComplete = false

    override fun viewListeners() {
        val idSwabReport = intent.getIntExtra("idSwabReport",-1)
        val idEstadoSwab = intent.getIntExtra("idEstadoSwab",-1)

        Log.e("SERGIO", " -- idEstadoIncidencia = $idEstadoSwab")
        val title = "Inspecciones Orden Swab # $idSwabReport"

        binding.toolbar.title = title

        binding.apply {
            rvIncidences.adapter = adapter

            adapter.onEditIncidenciaClickListener = { it, pos ->
                openActivity(IncidenceDetailActivity::class.java, extras = {
                    putInt("idRegistro",it.idregistro)
                    putBoolean("isComplete",isComplete)
                    putInt("idSwabReporte",it.idSwabReporte)
                    putInt("idEstadoSwab",it.idSwabReporte)
                    putString("title","Inspección #${pos + 1}")
                })
            }

            adapter.onAnexoKClickListener = { it, pos ->
                openActivity(AnexoKNuevoActivity::class.java, extras = {
                    val idReg = it.idregistro
                    putInt("idRegistro", idReg)
                    putBoolean("isComplete",isComplete)
                    putInt("idEstadoSwab",idEstadoSwab)
                    putInt("idSwabReport",idSwabReport)
                    putString("title","AnexoK - Inspeccion #${idReg}")
                })
            }

            imgAddIncidence.setOnClickListener {
                alertDialog.message = "¿Está seguro que desea agregar una nueva inspección de cisterna?"
                alertDialog.onAcceptClickListener = {
                    val swabIncidencesEntity = SwabIncidencesEntity(
                        idSwabReporte = idSwabReport,
                        idpozo = "",
                        pozo="",
                        bat = "",
                        tipsReviso = 1,
                        horasPresion = "",
                        horasInicio = "",
                        horasMd = "",
                        horasPist = "",
                        horasMant = "",
                        horasParada = "",
                        horasTermino = "",
                        diamCstb = "",
                        diamNa = "",
                        nivelesInicial = "",
                        nivelesFinal = "",
                        corr = "",
                        produccionPet = "",
                        produccionAgua = "",
                        estadoApp = 0,
                        estadoIncidencia = 0,
                        tieneAnexoK = 0
                    )
                    Log.e("SERGIO", "IncidenceActivity swabIncidencesEntity=$swabIncidencesEntity")
                    viewModel.addIncidence(swabIncidencesEntity)
                }
                alertDialog.show()
            }
        }
        viewModel.getIncidences(idSwabReport)
    }

    override fun onResume() {
        super.onResume()
        val idSwabReport = intent.getIntExtra("idSwabReport",-1)
        viewModel.getIncidences(idSwabReport)
    }

    override fun viewModelListeners() {
        viewModel.apply {
            getStadoSwab.observe(this@IncidenceActivity){
                isComplete = it
                binding.imgAddIncidence.visibility = if(it) View.GONE else View.VISIBLE
            }
            getIncidencesSuccess.observe(this@IncidenceActivity){
                adapter.list = it
                binding.txtMessage.visibility =  if (it.count() > 0) View.GONE else View.VISIBLE
            }

            addIncidenceSuccess.observe(this@IncidenceActivity){
                viewModel.getIncidences(intent.getIntExtra("idSwabReport",-1))
                showMessage(it)
            }

        }
    }

}