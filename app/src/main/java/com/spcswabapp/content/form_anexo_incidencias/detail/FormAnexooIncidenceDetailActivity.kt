package com.spcswabapp.content.form_anexo_incidencias.detail

import android.app.TimePickerDialog
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.spcswabapp.R
import com.spcswabapp.base.ActivityViewBinding
import com.spcswabapp.content.new_order.fragments.*
import com.spcswabapp.database.entities.FormAnexooIncidencesEntity
import com.spcswabapp.databinding.ActivityIncidence2DetailBinding
import com.spcswabapp.utils.disable
import com.spcswabapp.utils.hideKeyboard
import com.spcswabapp.utils.showMessage
import java.text.SimpleDateFormat
import java.util.*
import com.spcswabapp.sergiofunciones.SergioFunciones

@Suppress("SpellCheckingInspection")
class FormAnexooIncidenceDetailActivity : ActivityViewBinding<ActivityIncidence2DetailBinding, FormAnexooIncidenceDetailVM>() {
    private lateinit var incidence : FormAnexooIncidencesEntity
    private lateinit var etSelected : EditText

    override fun inflateLayout(layoutInflater: LayoutInflater) = ActivityIncidence2DetailBinding.inflate(layoutInflater)

    var isComplete = false
    var sergioFunciones = SergioFunciones()


    override fun viewListeners() {
        val idRegistro = intent.getIntExtra("idRegistro",-1)
        isComplete = intent.getBooleanExtra("isComplete",false)
        viewModel.getIncidence(idRegistro)
        binding.toolbar.title = intent.getStringExtra("title")

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            etSelected.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }


        binding.apply {

            /* SERGIO TEMPORAL */
            binding.imgLlenar.setOnClickListener {
                etKmInicial.setText(sergioFunciones.getStringValorAleatorioNumerico())
                etKmFinal.setText(sergioFunciones.getStringValorAleatorioNumerico())
                etHorometroInicial.setText(sergioFunciones.getStringValorAleatorioNumerico())
                etHorometroFinal.setText(sergioFunciones.getStringValorAleatorioNumerico())
                etEquipo.setText(sergioFunciones.getTextosAleatorios(10))
                etOrigen.setText(sergioFunciones.getTextosAleatorios(10))
                etDestino.setText(sergioFunciones.getTextosAleatorios(10))
                etHoraInicial.setText(sergioFunciones.getStringMinutosRandom())
                etHoraFinal.setText(sergioFunciones.getStringMinutosRandom())
                etOil.setText(sergioFunciones.getStringValorAleatorioNumerico())
                etAgua.setText(sergioFunciones.getStringValorAleatorioNumerico())
                etST.setText(sergioFunciones.getTextosAleatorios(5))
                etNPCROTO.setText(sergioFunciones.getTextosAleatorios(5))
                etNPCNUEVO.setText(sergioFunciones.getTextosAleatorios(5))
                etNPEROTO.setText(sergioFunciones.getTextosAleatorios(3))
                etNPENUEVO.setText(sergioFunciones.getTextosAleatorios(10))
                etTanque.setText(sergioFunciones.getTextosAleatorios(10))
                etObservaciones.setText(sergioFunciones.getTextosAleatorios(40))
            }

            imgDelete.setOnClickListener {
                alertDialog.message = "¿Está seguro que desea eliminar este reporte?"
                alertDialog.isDelete = true
                alertDialog.onAcceptClickListener = {
                    progressDialog.message = "Eliminando Reporte..."
                    progressDialog.show()
                    viewModel.deleteIncidence(idRegistro)
                }
                alertDialog.show()
            }

            etHoraInicial.setOnClickListener {
                etSelected = etHoraInicial
                TimePickerDialog(this@FormAnexooIncidenceDetailActivity,
                    R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }

            etHoraFinal.setOnClickListener {
                etSelected = etHoraFinal
                TimePickerDialog(this@FormAnexooIncidenceDetailActivity,
                    R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

            }

            imgSave.setOnClickListener {
                alertDialog.message = "¿Esta seguro que desea guardar los cambios?"
                alertDialog.isDelete = false
                alertDialog.onAcceptClickListener = {
                    hideKeyboard()
                    progressDialog.message = "Guardando datos..."
                    progressDialog.show()
                    incidence.apply {
                        kmInicial = etKmInicial.text.toString()
                        kmFinal = etKmFinal.text.toString()
                        horometroInicial = etHorometroInicial.text.toString()
                        horometroFinal = etHorometroFinal.text.toString()
                        equipo = etEquipo.text.toString()
                        origen = etOrigen.text.toString()
                        destino = etDestino.text.toString()
                        horaInicial = etHoraInicial.text.toString()
                        horaFinal = etHoraFinal.text.toString()
                        oil = etOil.text.toString()
                        agua = etAgua.text.toString()
                        s_t = etST.text.toString()
                        npcRoto  = etNPCROTO.text.toString()
                        npcNuevo = etNPCNUEVO.text.toString()
                        npeRoto = etNPEROTO.text.toString()
                        npeNuevo = etNPENUEVO.text.toString()
                        tanque_descarga_zona = etTanque.text.toString()
                        observaciones = etObservaciones.text.toString()

                    }
                    viewModel.saveIncidence(incidence)
                    Handler(Looper.myLooper()!!).postDelayed({
                        showMessage("Datos guardados correctamente")
                        progressDialog.hide()
                    },1500)
                    finish()
                }
                alertDialog.show()
            }
        }

        checkStatus()
    }


    private fun checkStatus(){
        binding.apply {
            if(isComplete){
                imgDelete.visibility = View.GONE
                imgSave.visibility = View.GONE

                /* Sergio temporal */
                imgLlenar.visibility = View.GONE
                /* Fin Sergio*/
                etKmInicial.disable()
                etKmFinal.disable()
                etHorometroInicial.disable()
                etHorometroFinal.disable()
                etEquipo.disable()
                etOrigen.disable()
                etDestino.disable()
                etHoraInicial.disable()
                etHoraFinal.disable()
                etOil.disable()
                etAgua.disable()
                etST.disable()
                etNPCROTO.disable()
                etNPCNUEVO.disable()
                etNPEROTO.disable()
                etNPENUEVO.disable()
                etTanque.disable()
                etObservaciones.disable()

            }else{
                imgDelete.visibility = View.VISIBLE
                imgSave.visibility = View.VISIBLE

                /* Sergio temporal */
                //imgLlenar.visibility = View.VISIBLE
                imgLlenar.visibility = View.GONE
                /* Fin Sergio*/
            }
        }
    }


    override fun viewModelListeners() {
        viewModel.apply {
            deleteSuccess.observe(this@FormAnexooIncidenceDetailActivity){
                showMessage(it)
                progressDialog.hide()
                finish()
            }

            saveSuccess.observe(this@FormAnexooIncidenceDetailActivity){
                showMessage(it)
                progressDialog.hide()
            }

            getIncidenceSuccess.observe(this@FormAnexooIncidenceDetailActivity){
                incidence = it
                binding.apply {
                    etKmInicial.setText(it.kmInicial)
                    etKmFinal.setText(it.kmFinal)
                    etHorometroInicial.setText(it.horometroInicial)
                    etHorometroFinal.setText(it.horometroFinal)
                    etEquipo.setText(it.equipo)
                    etOrigen.setText(it.origen)
                    etDestino.setText(it.destino)
                    etHoraInicial.setText(it.horaInicial)
                    etHoraFinal.setText(it.horaFinal)
                    etOil.setText(it.oil)
                    etAgua.setText(it.agua)
                    etST.setText(it.s_t)
                    etNPCROTO.setText(it.npcRoto)
                    etNPCNUEVO.setText(it.npcNuevo)
                    etNPEROTO.setText(it.npeRoto)
                    etNPENUEVO.setText(it.npeNuevo)
                    etTanque.setText(it.tanque_descarga_zona)
                    etObservaciones.setText(it.observaciones)
                }
            }
        }
    }

}