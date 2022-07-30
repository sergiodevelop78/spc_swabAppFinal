package com.spcswabapp.content.incidences.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.spcswabapp.base.BaseAdapter
import com.spcswabapp.database.entities.SwabIncidencesEntity
import com.spcswabapp.databinding.IncidenceItemBinding

@Suppress("SpellCheckingInspection")

class IncidenceAdapter : BaseAdapter<SwabIncidencesEntity,IncidenceItemBinding>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachParent: Boolean
    )=  IncidenceItemBinding.inflate(layoutInflater,parent,attachParent)

    var onEditIncidenciaClickListener: ((SwabIncidencesEntity, String) -> Unit)? = null
    var onAnexoKClickListener : ((SwabIncidencesEntity, String) -> Unit) ? = null

    @SuppressLint("SetTextI18n")
    override fun onItemViewReady(
        binding: IncidenceItemBinding,
        item: SwabIncidencesEntity,
        position: Int
    ) {
        val idOrdenSwab = item.idSwabReporte.toString()
        val estadoIncidencia = item.estadoIncidencia  // 0 = Anexo I incompleto, 1=completo

        var msgEstadoIncidencia = ""
        var msgEstadoAnexoK = ""

        Log.d("SERGIO", "Incidence Adapter IDORDENSWAB=$idOrdenSwab")
        Log.d("SERGIO", "Incidence Adapter estadoIncidencia=$estadoIncidencia")

        binding.apply {
            txtTitle.text = "Inspección #${position + 1}"

            if (item.estadoIncidencia == 1){
                txtEstado.setTextColor((Color.parseColor("#000000")))
                /* Inspeccion completa */
                msgEstadoIncidencia = "Completo"
            }
            else {
                txtEstado.setTextColor((Color.parseColor("#F44336")))
                /* Inspeccion incompleta */
                msgEstadoIncidencia = "pendiente"
            }

            if (item.tieneAnexoK == 1){
                txtAnexoK.setTextColor((Color.parseColor("#000000")))
                msgEstadoAnexoK = "Anexo K - Completo"
            }
            else {
                txtAnexoK.setTextColor((Color.parseColor("#F44336")))
                msgEstadoAnexoK = "Anexo K pendiente"
            }


            txtEstado.text = "Anexo I $msgEstadoIncidencia"
            txtAnexoK.text = msgEstadoAnexoK

            llEditarIncidencia.setOnClickListener {
                swipeRevealLayout.close(true)
                Log.d("SERGIO", "## CLIC EN EDITAR INSPECCION")
                onEditIncidenciaClickListener?.invoke(list[position], idOrdenSwab)

            }

            llAnexoK.setOnClickListener {
                swipeRevealLayout.close(true)

                if (estadoIncidencia==1) { // en Progreso  1=En progreso , 0=No en progreso
                    Log.d("SERGIO", "## CLIC EN CREAR ANEXO K")
                    onAnexoKClickListener?.invoke(list[position], idOrdenSwab)
                }
                else {
                    val mensajeError = "No se puede crear/editar un Formulario K, mientras el anexo I siga incompleto"
                    //Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    val dialogo = AlertDialog.Builder(it.context)

                    dialogo.setTitle("Error")
                        .setMessage(mensajeError)
                        .setPositiveButton("Ok") { _, _ ->
                            // no hacer nada
                        }
                    dialogo.create()
                    dialogo.show()
                }


            }

        }

    }




}
