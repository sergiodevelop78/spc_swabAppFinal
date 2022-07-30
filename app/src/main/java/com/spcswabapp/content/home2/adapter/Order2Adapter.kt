package com.spcswabapp.content.home2.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spcswabapp.base.BaseAdapter
import com.spcswabapp.database.entities.FormAnexooReportEntity
import com.spcswabapp.databinding.Order2ItemBinding

class Order2Adapter : BaseAdapter<FormAnexooReportEntity, Order2ItemBinding>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachParent: Boolean
    ) = Order2ItemBinding.inflate(layoutInflater, parent, attachParent)

    var onEditClickListener: ((FormAnexooReportEntity,String) -> Unit)? = null
    var onIncidenceClickListener: ((FormAnexooReportEntity) -> Unit)? = null
    var onRegistClickListener : ((FormAnexooReportEntity) -> Unit) ? = null

    @SuppressLint("SetTextI18n")
    override fun onItemViewReady(binding: Order2ItemBinding, item: FormAnexooReportEntity, position: Int) {
        binding.apply {
            txtOrder.text = "Form Anexo O #${position + 1}"
            txtStatus.text =
                "Estado: " + if (list[position].idEstado == 1) "Registrado"
                else if (list[position].idEstado == 2) "En Curso"
                else "Finalizada"
            txtDate.text = "Fecha registro: ${list[position].fechaRegistro}"

            if(item.idEstado == 3 || item.idEstado == 1){
                llRegist.visibility = View.GONE
            }else{
                llRegist.visibility = View.VISIBLE
            }

            if(item.idEstado == 3){
                  txtStatus.setTextColor((Color.parseColor("#F44336")))
            }

            llEdit.setOnClickListener {
                swipeRevealLayout.close(true)
                onEditClickListener?.invoke(list[position],txtOrder.text.toString())

            }

            llRegist.setOnClickListener {
                swipeRevealLayout.close(true)
                onRegistClickListener?.invoke(list[position])
            }

            llIncidences.setOnClickListener {
                swipeRevealLayout.close(true)
                onIncidenceClickListener?.invoke(list[position])
            }

        }
    }
}