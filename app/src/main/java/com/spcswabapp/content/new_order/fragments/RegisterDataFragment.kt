package com.spcswabapp.content.new_order.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.new_order.NewOrderActivity
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.database.entities.SwabReportEntity
import com.spcswabapp.databinding.FragmentRegisterDataBinding
import com.spcswabapp.utils.disable
import com.spcswabapp.utils.hideKeyboard
import java.text.SimpleDateFormat
import com.spcswabapp.sergiofunciones.SergioFunciones


class RegisterDataFragment : FragmentViewBinding<FragmentRegisterDataBinding, NewOrderVM>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentRegisterDataBinding.inflate(layoutInflater, viewGroup, boolean)

    lateinit var swabReportEntity : SwabReportEntity
    var sergioFunciones =  SergioFunciones()

    override fun initViews() {
        viewModel.getSwabReport((requireActivity() as NewOrderActivity).swabReportEntity.idSwabReporte)

    }

    fun saveData(){
        hideKeyboard()
        swabReportEntity.comentarios = binding.etComment.text.toString()
        swabReportEntity.csms = binding.etCSMS.text.toString()
        viewModel.saveSwabReport(swabReportEntity)
    }


    fun validarCampos(): Boolean {
        return binding.etComment.text.isNotEmpty() || binding.etCSMS.text.isNotEmpty()

    }

    fun llenarCamposRandom() {
        binding.etComment.setText(sergioFunciones.getTextosAleatorios(20))
        binding.etCSMS.setText(sergioFunciones.getTextosAleatorios(10))
    }

    @SuppressLint("SimpleDateFormat")
    override fun viewModelListeners() {
        viewModel.apply {
            getDataSuccess.observe(this@RegisterDataFragment){
                binding.etDevice.setText(it.equipoTrabajoDesc)
                binding.etTurn.setText(it.turnoDesc)
            }

            getSwabReportSuccess.observe(this@RegisterDataFragment){
                swabReportEntity = it
                checkStatus()
                val date = SimpleDateFormat("dd-MM-yyyy hh:mm").parse(it.fechaRegistro)
                val dateFormant = SimpleDateFormat("dd-MM-yyyy").format(date)
                val timeFormat = SimpleDateFormat("HH:mm:ss").format(date)
                binding.etDate.setText(dateFormant)
                binding.etTime.setText(timeFormat.substring(0,5))
                binding.etComment.setText(it.comentarios)
                binding.etCSMS.setText(it.csms)
            }
        }
    }

    fun checkStatus(){
        if(swabReportEntity.idEstadoSwab == 3){
            binding.apply {
                etCSMS.disable()
                etComment.disable()
                etTime.disable()
            }
        }
    }




}