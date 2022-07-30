package com.spcswabapp.content.new_order.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.new_order.NewOrderActivity
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.database.entities.SwabDayliConsumptionMaterialsEntity
import com.spcswabapp.databinding.FragmentDayliConsumptionMaterialsBinding
import com.spcswabapp.sergiofunciones.SergioFunciones
import com.spcswabapp.utils.disable


class DayliConsumptionMaterialsFragment :
    FragmentViewBinding<FragmentDayliConsumptionMaterialsBinding, NewOrderVM>() {

    private lateinit var swabDayliConsumptionMaterialsEntity: SwabDayliConsumptionMaterialsEntity

    var sergioFunciones = SergioFunciones()

    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentDayliConsumptionMaterialsBinding.inflate(layoutInflater, viewGroup, boolean)

    override fun initViews() {
        checkStatus()
        viewModel.getSwabConsumptionDayliMaterials((requireActivity() as NewOrderActivity).swabReportEntity.idSwabReporte)

    }

    fun llenarCamposRandom() {
        binding.etMnrCup1.setText( sergioFunciones.getTextosAleatorios(4))
        binding.etMnrCup2.setText(sergioFunciones.getTextosAleatorios(4))
        binding.etMnrCup3.setText(sergioFunciones.getTextosAleatorios(3))
        binding.etMnrCup4.setText(sergioFunciones.getTextosAleatorios(2))
        binding.etMnrEco.setText(sergioFunciones.getTextosAleatorios(3))

        binding.etMsCup1.setText( sergioFunciones.getTextosAleatorios(4))
        binding.etMsCup2.setText(sergioFunciones.getTextosAleatorios(4))
        binding.etMsCup3.setText(sergioFunciones.getTextosAleatorios(3))
        binding.etMsCup4.setText(sergioFunciones.getTextosAleatorios(2))
        binding.etMsEco.setText(sergioFunciones.getTextosAleatorios(3))

        binding.etMndCup1.setText( sergioFunciones.getTextosAleatorios(4))
        binding.etMndCup2.setText(sergioFunciones.getTextosAleatorios(4))
        binding.etMndCup3.setText(sergioFunciones.getTextosAleatorios(3))
        binding.etMndCup4.setText(sergioFunciones.getTextosAleatorios(2))
        binding.etMndEco.setText(sergioFunciones.getTextosAleatorios(3))

        binding.etMdaCup1.setText( sergioFunciones.getTextosAleatorios(4))
        binding.etMdaCup2.setText(sergioFunciones.getTextosAleatorios(4))
        binding.etMdaCup3.setText(sergioFunciones.getTextosAleatorios(3))
        binding.etMdaCup4.setText(sergioFunciones.getTextosAleatorios(2))
        binding.etMdaEco.setText(sergioFunciones.getTextosAleatorios(3))

    }


    private fun checkStatus() {
        if ((requireActivity() as NewOrderActivity).swabReportEntity.idEstadoSwab == 3) {
            binding.apply {
                etMnrCup1.disable()
                etMnrCup2.disable()
                etMnrCup3.disable()
                etMnrCup4.disable()
                etMnrEco.disable()

                etMsCup1.disable()
                etMsCup2.disable()
                etMsCup3.disable()
                etMsCup4.disable()
                etMsEco.disable()

                etMndCup1.disable()
                etMndCup2.disable()
                etMndCup3.disable()
                etMndCup4.disable()
                etMndEco.disable()

                etMdaCup1.disable()
                etMdaCup2.disable()
                etMdaCup3.disable()
                etMdaCup4.disable()
                etMdaEco.disable()
            }
        }
    }

    fun saveData() {
        swabDayliConsumptionMaterialsEntity.mnrCopa1 = binding.etMnrCup1.text.toString()
        swabDayliConsumptionMaterialsEntity.mnrCopa2 = binding.etMnrCup2.text.toString()
        swabDayliConsumptionMaterialsEntity.mnrCopa3 = binding.etMnrCup3.text.toString()
        swabDayliConsumptionMaterialsEntity.mnrCopa4 = binding.etMnrCup4.text.toString()
        swabDayliConsumptionMaterialsEntity.mnrEco = binding.etMnrEco.text.toString()

        swabDayliConsumptionMaterialsEntity.msCopa1 = binding.etMsCup1.text.toString()
        swabDayliConsumptionMaterialsEntity.msCopa2 = binding.etMsCup2.text.toString()
        swabDayliConsumptionMaterialsEntity.msCopa3 = binding.etMsCup3.text.toString()
        swabDayliConsumptionMaterialsEntity.msCopa4 = binding.etMsCup4.text.toString()
        swabDayliConsumptionMaterialsEntity.msEco = binding.etMsEco.text.toString()

        swabDayliConsumptionMaterialsEntity.mndCopa1 = binding.etMndCup1.text.toString()
        swabDayliConsumptionMaterialsEntity.mndCopa2 = binding.etMndCup2.text.toString()
        swabDayliConsumptionMaterialsEntity.mndCopa3 = binding.etMndCup3.text.toString()
        swabDayliConsumptionMaterialsEntity.mndCopa4 = binding.etMndCup4.text.toString()
        swabDayliConsumptionMaterialsEntity.mndEco = binding.etMndEco.text.toString()

        swabDayliConsumptionMaterialsEntity.mdaCopa1 = binding.etMdaCup1.text.toString()
        swabDayliConsumptionMaterialsEntity.mdaCopa2 = binding.etMdaCup2.text.toString()
        swabDayliConsumptionMaterialsEntity.mdaCopa3 = binding.etMdaCup3.text.toString()
        swabDayliConsumptionMaterialsEntity.mdaCopa4 = binding.etMdaCup4.text.toString()
        swabDayliConsumptionMaterialsEntity.mdaEco = binding.etMdaEco.text.toString()
        viewModel.saveSwabConsumptionDayliMaterials(swabDayliConsumptionMaterialsEntity)
    }

    override fun viewModelListeners() {
        viewModel.apply {
            getSwabConsumptionDayliMaterials.observe(this@DayliConsumptionMaterialsFragment) {
                swabDayliConsumptionMaterialsEntity = it
                binding.apply {
                    etMnrCup1.setText(it.mnrCopa1)
                    etMnrCup2.setText(it.mnrCopa2)
                    etMnrCup3.setText(it.mnrCopa3)
                    etMnrCup4.setText(it.mnrCopa4)
                    etMnrEco.setText(it.mnrEco)

                    etMsCup1.setText(it.msCopa1)
                    etMsCup2.setText(it.msCopa2)
                    etMsCup3.setText(it.msCopa3)
                    etMsCup4.setText(it.msCopa4)
                    etMsEco.setText(it.msEco)

                    etMndCup1.setText(it.mndCopa1)
                    etMndCup2.setText(it.mndCopa2)
                    etMndCup3.setText(it.mndCopa3)
                    etMndCup4.setText(it.mndCopa4)
                    etMndEco.setText(it.mndEco)

                    etMdaCup1.setText(it.mdaCopa1)
                    etMdaCup2.setText(it.mdaCopa2)
                    etMdaCup3.setText(it.mdaCopa3)
                    etMdaCup4.setText(it.mdaCopa4)
                    etMdaEco.setText(it.mdaEco)
                }
            }
        }
    }


}