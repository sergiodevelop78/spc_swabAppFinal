package com.spcswabapp.content.form_anexoo.fragments

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.form_anexoo.NewFormAnexoOActivity
import com.spcswabapp.content.form_anexoo.NewFormAnexoOVM
import com.spcswabapp.database.entities.FormAnexooReportEntity
import com.spcswabapp.databinding.FragmentRegisterDataFormanexooBinding
import com.spcswabapp.sergiofunciones.SergioFunciones
import com.spcswabapp.utils.disable
import com.spcswabapp.utils.hideKeyboard
import java.text.SimpleDateFormat


class FormAnexoORegisterDataFragment : FragmentViewBinding<FragmentRegisterDataFormanexooBinding, NewFormAnexoOVM>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentRegisterDataFormanexooBinding.inflate(layoutInflater, viewGroup, boolean)



    lateinit var formAnexooReportEntity : FormAnexooReportEntity

    var sergioFunciones =  SergioFunciones()

    override fun initViews() {
        viewModel.getFormAnexooReport((requireActivity() as NewFormAnexoOActivity).formAnexooReportEntity.idFormAnexoo)
    }


    @SuppressLint("SimpleDateFormat")
    fun saveData(){
        hideKeyboard()
        var fechaTxt : String = binding.etDate.text.toString()

        formAnexooReportEntity.fecha = fechaTxt
        formAnexooReportEntity.hora = binding.etTime.text.toString()
        formAnexooReportEntity.lugarRelevo = binding.etLugarDeRelevo.text.toString()
        formAnexooReportEntity.nroPrecinto = binding.etNroPrecinto.text.toString()
        formAnexooReportEntity.cisterna = binding.etCisterna.text.toString()
        formAnexooReportEntity.volumen =  binding.etVolumen.text.toString()
        formAnexooReportEntity.macsObs = binding.etMACS.text.toString()

        Log.d("SERGIO", formAnexooReportEntity.toString())
        viewModel.saveFormAnexooReport(formAnexooReportEntity)
    }


    @SuppressLint("SimpleDateFormat")
    override fun viewModelListeners() {
        viewModel.apply {
            getDataSuccess.observe(this@FormAnexoORegisterDataFragment){
                //binding.etMACS.setText(it.equipoTrabajoDesc)
                binding.etTurn.setText(it.turnoDesc)
            }

            getFormAnexooReportSuccess.observe(this@FormAnexoORegisterDataFragment){
                formAnexooReportEntity = it
                checkStatus()
                val date = SimpleDateFormat("dd-MM-yyyy hh:mm").parse(it.fechaRegistro)
                val dateFormant = SimpleDateFormat("yyyy-MM-dd").format(date)
                val timeFormat = SimpleDateFormat("HH:mm").format(date)
                binding.etDate.setText(dateFormant)
                binding.etTime.setText(timeFormat.substring(0,5))
                binding.etMACS.setText(it.macsObs)
                binding.etCisterna.setText(it.cisterna)
                binding.etLugarDeRelevo.setText(it.lugarRelevo)
                binding.etVolumen.setText(it.volumen)
                binding.etNroPrecinto.setText(it.nroPrecinto)
            }
        }
    }

    fun checkStatus(){
        if(formAnexooReportEntity.idEstado == 3){
            binding.apply {
                etDate.disable()
                etTime.disable()
                etMACS.disable()
                etCisterna.disable()
                etLugarDeRelevo.disable()
                etVolumen.disable()
                etNroPrecinto.disable()

            }
        }
    }

    /* Sergio Temporal para pruebas */
    fun llenarCamposRandom() {
        binding.etCisterna.setText(sergioFunciones.getTextosAleatorios(6))
        binding.etLugarDeRelevo.setText(sergioFunciones.getTextosAleatorios(8))
        binding.etNroPrecinto.setText(sergioFunciones.getTextosAleatorios(5))
        binding.etVolumen.setText(sergioFunciones.getStringValorAleatorioNumerico())
        binding.etMACS.setText(sergioFunciones.getTextosAleatorios(40))
    }




}