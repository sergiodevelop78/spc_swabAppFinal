package com.spcswabapp.content.new_order.fragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.R
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.new_order.NewOrderActivity
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.database.entities.SwabFuelEntity
import com.spcswabapp.databinding.FragmentFuelBinding
import com.spcswabapp.sergiofunciones.SergioFunciones
import com.spcswabapp.utils.disable
import java.text.SimpleDateFormat
import java.util.*


class FuelFragment : FragmentViewBinding<FragmentFuelBinding, NewOrderVM>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentFuelBinding.inflate(layoutInflater, viewGroup, boolean)
    private lateinit var swabFuelEntity: SwabFuelEntity

    var sergioFunciones = SergioFunciones()

    @SuppressLint("SimpleDateFormat")
    override fun initViews() {
        viewModel.getSwabFuel((requireActivity() as NewOrderActivity).swabReportEntity.idSwabReporte)


        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.etTime.setText(SimpleDateFormat("HH:mm:ss").format(cal.time).substring(0,5))
        }

        binding.etTime.setOnClickListener {
            TimePickerDialog(context,R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        checkStatus()

    }


    fun llenarCamposRandom() {
        binding.etGalones.setText( sergioFunciones.getStringValorAleatorioNumerico())
        binding.etTime.setText(sergioFunciones.getStringMinutosRandom())
        binding.etHorometerIni.setText(sergioFunciones.getStringValorAleatorioNumerico())
        binding.etHorometerEnd.setText(sergioFunciones.getStringValorAleatorioNumerico())
        binding.etObservations.setText(sergioFunciones.getTextosAleatorios(18))

    }


    private fun checkStatus(){
        if((requireActivity() as NewOrderActivity).swabReportEntity.idEstadoSwab == 3){
            binding.apply {
                etGalones.disable()
                etObservations.disable()
                etHorometerEnd.disable()
                etHorometerIni.disable()
                etTime.disable()
            }
        }
    }

    fun saveData(){
        swabFuelEntity.galones = binding.etGalones.text.toString()
        swabFuelEntity.observaciones = binding.etObservations.text.toString()
        swabFuelEntity.horometroFin = binding.etHorometerEnd.text.toString()
        swabFuelEntity.horometroIni = binding.etHorometerIni.text.toString()
        swabFuelEntity.hora = binding.etTime.text.toString()
        viewModel.saveSwabFuel(swabFuelEntity)
    }

    override fun viewModelListeners() {
        viewModel.getSwabFuelSuccess.observe(this){
            swabFuelEntity = it
            binding.apply {
                etGalones.setText(it.galones)
                etTime.setText(it.hora)
                etHorometerIni.setText(it.horometroIni)
                etHorometerEnd.setText(it.horometroFin)
                etObservations.setText(it.observaciones)
            }
        }
    }


}