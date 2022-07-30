package com.spcswabapp.content.new_order.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.FragmentViewBinding
import com.spcswabapp.content.new_order.NewOrderActivity
import com.spcswabapp.content.new_order.NewOrderVM
import com.spcswabapp.content.new_order.fragments.adapter.CriticalPointAdapter
import com.spcswabapp.database.entities.SwabCriticalPointEntity
import com.spcswabapp.databinding.FragmentCriticalPointVerificationBinding


class CriticalPointVerificationFragment :
    FragmentViewBinding<FragmentCriticalPointVerificationBinding, NewOrderVM>() {


    private val swabUnityAdapter = CriticalPointAdapter()
    private val tankAdapter = CriticalPointAdapter()
    private var unityCriticalPointList = ArrayList<SwabCriticalPointEntity>()
    private var tankCriticalPointList = ArrayList<SwabCriticalPointEntity>()

    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ) = FragmentCriticalPointVerificationBinding.inflate(layoutInflater, viewGroup, boolean)




    override fun initViews() {
        binding.apply {
            rvSwabUnity.adapter = swabUnityAdapter
            rvTank.adapter = tankAdapter

        }
        viewModel.getSwabCriticalPoint((requireActivity() as NewOrderActivity).swabReportEntity.idSwabReporte)

    }


    fun saveData(){
        viewModel.saveSwabCriticalPoint(unityCriticalPointList)
        viewModel.saveSwabCriticalPoint(tankCriticalPointList)
    }

    override fun viewModelListeners() {

        viewModel.apply {
            getTankSuccess.observe(this@CriticalPointVerificationFragment){
                tankCriticalPointList = it
                if((requireActivity() as NewOrderActivity).swabReportEntity.idEstadoSwab == 3){
                    tankAdapter.isDisable = true
                }
                tankAdapter.list =it
            }
            getSwabUnitySuccess.observe(this@CriticalPointVerificationFragment){
                unityCriticalPointList = it
                if((requireActivity() as NewOrderActivity).swabReportEntity.idEstadoSwab == 3){
                    swabUnityAdapter.isDisable = true
                }
                swabUnityAdapter.list = it
            }

        }
    }


}