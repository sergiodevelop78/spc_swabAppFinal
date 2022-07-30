package com.spcswabapp.content.new_order.fragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import com.spcswabapp.R
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.new_order.NewOrderActivity
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.database.entities.SwabTankDispatchEntity
import com.spcswabapp.databinding.FragmentTankDispatchBinding
import com.spcswabapp.sergiofunciones.SergioFunciones
import com.spcswabapp.utils.disable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TankDispatchFragment : FragmentViewBinding<FragmentTankDispatchBinding,NewOrderVM>() {

    private lateinit var swabTankDispatchList : ArrayList<SwabTankDispatchEntity>
    private lateinit var etSelected : EditText

    var sergioFunciones = SergioFunciones()

    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentTankDispatchBinding.inflate(layoutInflater,viewGroup,boolean)

    @SuppressLint("SimpleDateFormat")
    override fun initViews() {

        viewModel.getSwabTankDispatch((requireActivity() as NewOrderActivity).swabReportEntity.idSwabReporte)

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            etSelected.setText(SimpleDateFormat("HH:mm:ss").format(cal.time).substring(0,5))
        }



        binding.apply {
            etTimeEnd1.setOnClickListener {
                etSelected = etTimeEnd1
                TimePickerDialog(context,R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }
            etTimeEnd2.setOnClickListener {
                etSelected = etTimeEnd2
                TimePickerDialog(context,R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }

            etTimeInit1.setOnClickListener {
                etSelected = etTimeInit1
                TimePickerDialog(context,R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }

            etTimeInit2.setOnClickListener {
                etSelected = etTimeInit2
                TimePickerDialog(context,R.style.DialogTheme,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }
        }
        checkStatus()
    }


    fun llenarCamposRandom() {
        binding.etNumTank1.setText( sergioFunciones.getStringValorAleatorioNumerico())
        binding.etTimeEnd1.setText(sergioFunciones.getStringMinutosRandom())
        binding.etPozo1.setText( sergioFunciones.getTextosAleatorios(8))
        binding.etCantDesp1.setText(sergioFunciones.getStringValorAleatorioNumerico())
        binding.etLugDesc1.setText( sergioFunciones.getTextosAleatorios(10))
        binding.etCist1.setText(sergioFunciones.getTextosAleatorios(8))
        binding.etProced1.setText( sergioFunciones.getTextosAleatorios(12))
        binding.etTimeInit1.setText(sergioFunciones.getStringMinutosRandom())
        binding.etPozo11.setText( sergioFunciones.getTextosAleatorios(8))

        binding.etNumTank2.setText( sergioFunciones.getStringValorAleatorioNumerico())
        binding.etTimeEnd2.setText(sergioFunciones.getStringMinutosRandom())
        binding.etPozo2.setText( sergioFunciones.getTextosAleatorios(8))
        binding.etCantDesp2.setText(sergioFunciones.getStringValorAleatorioNumerico())
        binding.etLugDesc2.setText( sergioFunciones.getTextosAleatorios(10))
        binding.etCist2.setText(sergioFunciones.getTextosAleatorios(8))
        binding.etProced2.setText( sergioFunciones.getTextosAleatorios(12))
        binding.etTimeInit2.setText(sergioFunciones.getStringMinutosRandom())
        binding.etPozo22.setText( sergioFunciones.getTextosAleatorios(8))



    }

    private fun checkStatus(){
        if((requireActivity() as NewOrderActivity).swabReportEntity.idEstadoSwab == 3){
            binding.apply {
                etNumTank1.disable()
                etTimeEnd1.disable()
                etPozo1.disable()
                etCantDesp1.disable()
                etLugDesc1.disable()
                etCist1.disable()
                etProced1.disable()
                etTimeInit1.disable()
                etPozo11.disable()

                etNumTank2.disable()
                etTimeEnd2.disable()
                etPozo2.disable()
                etCantDesp2.disable()
                etLugDesc2.disable()
                etCist2.disable()
                etProced2.disable()
                etTimeInit2.disable()
                etPozo22.disable()
            }
        }
    }

    fun saveData(){
        swabTankDispatchList[0].apply {
            nroCisterna = binding.etNumTank1.text.toString()
            horaSalida = binding.etTimeEnd1.text.toString()
            pozo1 = binding.etPozo1.text.toString()
            cantidadDespachada = binding.etCantDesp1.text.toString()
            lugarDeDescarga = binding.etLugDesc1.text.toString()
            cisterna = binding.etCist1.text.toString()
            procedencia = binding.etProced1.text.toString()
            horaLlegada = binding.etTimeInit1.text.toString()
            pozo2 = binding.etPozo11.text.toString()
        }
        swabTankDispatchList[1].apply {
            nroCisterna = binding.etNumTank2.text.toString()
            horaSalida = binding.etTimeEnd2.text.toString()
            pozo1 = binding.etPozo2.text.toString()
            cantidadDespachada = binding.etCantDesp2.text.toString()
            lugarDeDescarga = binding.etLugDesc2.text.toString()
            cisterna = binding.etCist2.text.toString()
            procedencia = binding.etProced2.text.toString()
            horaLlegada = binding.etTimeInit2.text.toString()
            pozo2 = binding.etPozo22.text.toString()
        }
        viewModel.saveSwabTankDispatch(swabTankDispatchList)
    }

    override fun viewModelListeners() {
        viewModel.apply {
            getSwabTankDispatch.observe(this@TankDispatchFragment){
                swabTankDispatchList = it
                binding.apply {
                    etNumTank1.setText(it[0].nroCisterna)
                    etTimeEnd1.setText(it[0].horaSalida)
                    etPozo1.setText(it[0].pozo1)
                    etCantDesp1.setText(it[0].cantidadDespachada)
                    etLugDesc1.setText(it[0].lugarDeDescarga)
                    etCist1.setText(it[0].cisterna)
                    etProced1.setText(it[0].procedencia)
                    etTimeInit1.setText(it[0].horaLlegada)
                    etPozo11.setText(it[0].pozo2)

                    etNumTank2.setText(it[1].nroCisterna)
                    etTimeEnd2.setText(it[1].horaSalida)
                    etPozo2.setText(it[1].pozo1)
                    etCantDesp2.setText(it[1].cantidadDespachada)
                    etLugDesc2.setText(it[1].lugarDeDescarga)
                    etCist2.setText(it[1].cisterna)
                    etProced2.setText(it[1].procedencia)
                    etTimeInit2.setText(it[1].horaLlegada)
                    etPozo22.setText(it[1].pozo2)
                }
            }
        }
    }


}