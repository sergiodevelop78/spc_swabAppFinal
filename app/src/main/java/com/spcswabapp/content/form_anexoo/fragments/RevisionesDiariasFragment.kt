package com.spcswabapp.content.form_anexoo.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.form_anexoo.NewFormAnexoOVM
import com.spcswabapp.content.form_anexoo.NewFormAnexoOActivity
import com.spcswabapp.content.form_anexoo.fragments.adapter.RevisionesDiariasAdapter
import com.spcswabapp.database.entities.FormAnexooRevisionesDiariasEntity
import com.spcswabapp.databinding.FragmentRevisionesdiariasVerificationBinding


class RevisionesDiariasFragment :
    FragmentViewBinding<FragmentRevisionesdiariasVerificationBinding, NewFormAnexoOVM>() {


    private val revisionesDiariasAdapter = RevisionesDiariasAdapter()
    private var revisionesDiariasList = ArrayList<FormAnexooRevisionesDiariasEntity>()

    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentRevisionesdiariasVerificationBinding.inflate(layoutInflater, viewGroup, boolean)


    override fun initViews() {
        binding.apply {
            rvRevDiariasUnity.adapter =  revisionesDiariasAdapter
        }
        viewModel.getFormAnexooRevisionesDiarias((requireActivity() as NewFormAnexoOActivity).formAnexooReportEntity.idFormAnexoo)
        //viewModel.getSwabCriticalPoint((requireActivity() as NewOrderActivity).swabReportEntity.idSwabReporte)
    }


    fun saveData(){
        Log.e("SERGIO", "REV DIARIAS FRAGMENT revisionesDiariasList = $revisionesDiariasList")
        viewModel.saveFormAnexooRevisionesDiarias(revisionesDiariasList)
    }


    override fun viewModelListeners() {
        viewModel.apply {
            getFormAnexoRevisionesDiariasSuccess.observe(this@RevisionesDiariasFragment){
                revisionesDiariasList = it
                if((requireActivity() as NewFormAnexoOActivity).formAnexooReportEntity.idEstado == 3){
                    revisionesDiariasAdapter.isDisable = true
                }
                revisionesDiariasAdapter.list = it
            }
        }
    }

    /* Sergio Temporal para pruebas */
    fun llenarCamposRandom() {
        //binding.etCisterna.setText(sergioFunciones.getTextosAleatorios(6))
        //binding.etLugarDeRelevo.setText(sergioFunciones.getTextosAleatorios(8))
    }


}