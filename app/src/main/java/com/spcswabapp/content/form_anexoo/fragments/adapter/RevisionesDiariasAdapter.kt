package com.spcswabapp.content.form_anexoo.fragments.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.spcswabapp.R
import com.spcswabapp.base.BaseAdapter
import com.spcswabapp.database.entities.FormAnexooRevisionesDiariasEntity
import com.spcswabapp.databinding.RevisionesDiariasItemBinding
import com.spcswabapp.utils.disable

class RevisionesDiariasAdapter : BaseAdapter<FormAnexooRevisionesDiariasEntity, RevisionesDiariasItemBinding>() {

    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachParent: Boolean
    ) = RevisionesDiariasItemBinding.inflate(layoutInflater, parent, attachParent)

    var isDisable = false


    override fun onItemViewReady(
        binding: RevisionesDiariasItemBinding,
        item: FormAnexooRevisionesDiariasEntity,
        position: Int
    ) {

        if(isDisable){
            binding.apply {
                etObservations.disable()
                rbGood.disable()
                rbBad.disable()
                rbNoTiene.disable()
                rbNoAplica.disable()

                rbGood.isEnabled = false
                rbBad.isEnabled = false
                rbNoTiene.isEnabled = false
                rbNoAplica.isEnabled = false
            }
        }

        binding.apply {
            txtTitle.text = item.nombre
            etObservations.setText(item.observaciones)

            rbGood.isChecked = item.estado == 1
            rbBad.isChecked = item.estado == 2
            rbNoTiene.isChecked = item.estado == 3
            rbNoAplica.isChecked = item.estado == 4

            /*rbGood.setOnCheckedChangeListener { _, b ->
                item.estado = if (b) 1 else 2
            }
            */

            rgMain.setOnCheckedChangeListener { group, checkedId  ->
                //var colorSelected = ""
                var estadoSelected = 0
                val color = when (checkedId){
                    R.id.rbGood -> {
                        //colorSelected = "Bueno"
                        estadoSelected = 1
                    }
                    R.id.rbBad -> {
                        //colorSelected = "Malo"
                        estadoSelected = 2
                    }
                    R.id.rbNoTiene -> {
                        //colorSelected = "No tiene"
                        estadoSelected = 3
                    }
                    R.id.rbNoAplica -> {
                        //colorSelected = "No aplica"
                        estadoSelected = 4
                    }
                    else -> estadoSelected = 1
                }
                //Log.e("SERGIO", "colorSelected = $colorSelected")
                //Log.e("SERGIO", "estadoSelected = $estadoSelected")

                item.estado = estadoSelected
            }


            etObservations.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    item.observaciones = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })

        }
    }
}