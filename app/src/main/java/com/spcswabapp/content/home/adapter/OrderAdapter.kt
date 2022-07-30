package com.spcswabapp.content.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spcswabapp.base.BaseAdapter
import com.spcswabapp.database.entities.SwabReportEntity
import com.spcswabapp.databinding.OrderItemBinding
import com.spcswabapp.sergiofunciones.SergioFunciones

private lateinit var sergioFunciones: SergioFunciones

@Suppress("SpellCheckingInspection")

class OrderAdapter : BaseAdapter<SwabReportEntity, OrderItemBinding>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachParent: Boolean
    ) = OrderItemBinding.inflate(layoutInflater, parent, attachParent)

    var onEditClickListener: ((SwabReportEntity,String) -> Unit)? = null
    var onIncidenceClickListener: ((SwabReportEntity, String) -> Unit)? = null
    var onRegistClickListener : ((SwabReportEntity) -> Unit) ? = null
    private var sergioFunciones = SergioFunciones()
    //var onDebugClickListener: ((SwabReportEntity, String) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onItemViewReady(binding: OrderItemBinding, item: SwabReportEntity, position: Int) {
        binding.apply {
            val posList = position+1
            val estadoList = list[position].idEstadoSwab
            sergioFunciones.showLogVerbose(list[position].toString())
            sergioFunciones.showLogVerbose("Posicion = $posList")
            sergioFunciones.showLogVerbose("Estado Orden Swab  = $estadoList")

            txtOrder.text = "Swab orden #${position + 1}"
            txtStatus.text =
                "Estado: " + if (list[position].idEstadoSwab == 1) "Registrado"
                else if (list[position].idEstadoSwab == 2) "En Curso"
                else if (list[position].idEstadoSwab == 3) "Finalizada"
                else if (list[position].idEstadoSwab == 4) "En Progreso"
                else "Desconocido"
            txtDate.text = "Fecha creación: ${list[position].fechaRegistro}"

            /* TEMPORAL BOTON DEBUG */
            //llDebug.visibility = View.VISIBLE
            llDebug.visibility = View.GONE

            if(item.idEstadoSwab == 1 || item.idEstadoSwab == 2 || item.idEstadoSwab == 3 ){
                llRegist.visibility = View.GONE
            }else{
                llRegist.visibility = View.VISIBLE
            }

            if(item.idEstadoSwab == 3){
                  txtStatus.setTextColor((Color.parseColor("#F44336")))
            }

            llEdit.setOnClickListener {
                swipeRevealLayout.close(true)
                onEditClickListener?.invoke(list[position],txtOrder.text.toString())

            }

            // boton Finalizar
            llRegist.setOnClickListener {
                swipeRevealLayout.close(true)
                onRegistClickListener?.invoke(list[position])
            }

            llIncidences.setOnClickListener {
                swipeRevealLayout.close(true)
                onIncidenceClickListener?.invoke(list[position], txtOrder.text.toString())
            }

            /*llDebug.setOnClickListener {
                swipeRevealLayout.close(true)

                onDebugClickListener?.invoke(list[position], txtOrder.text.toString())
            }
            */
        }
    }
}