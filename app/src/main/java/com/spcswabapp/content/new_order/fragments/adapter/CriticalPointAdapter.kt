package com.spcswabapp.content.new_order.fragments.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.BaseAdapter
import com.spcswabapp.database.entities.SwabCriticalPointEntity
import com.spcswabapp.databinding.CriticalPointItemBinding
import com.spcswabapp.utils.disable

class CriticalPointAdapter : BaseAdapter<SwabCriticalPointEntity, CriticalPointItemBinding>() {

    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachParent: Boolean
    ) = CriticalPointItemBinding.inflate(layoutInflater, parent, attachParent)

    var isDisable = false

    override fun onItemViewReady(
        binding: CriticalPointItemBinding,
        item: SwabCriticalPointEntity,
        position: Int
    ) {

        if(isDisable){
            binding.apply {
                etObservations.disable()
                rbGood.disable()
                rbBad.disable()
                rbGood.isEnabled = false
                rbBad.isEnabled = false
            }
        }

        binding.apply {
            txtTitle.text = item.nombre
            etObservations.setText(item.observaciones)
            rbGood.isChecked = item.estadoPc == 1
            rbBad.isChecked = item.estadoPc != 1

            rbGood.setOnCheckedChangeListener { _, b ->
               item.estadoPc = if (b) 1 else 2
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