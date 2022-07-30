package com.spcswabapp.content.form_anexo_incidencias.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.base.BaseAdapter
import com.spcswabapp.database.entities.FormAnexooIncidencesEntity
import com.spcswabapp.databinding.Incidence2ItemBinding

class FormAnexooIncidenceAdapter : BaseAdapter<FormAnexooIncidencesEntity,Incidence2ItemBinding>() {
    override fun bindingInflater(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachParent: Boolean
    )=  Incidence2ItemBinding.inflate(layoutInflater,parent,attachParent)

  

    @SuppressLint("SetTextI18n")
    override fun onItemViewReady(
        binding: Incidence2ItemBinding,
        item: FormAnexooIncidencesEntity,
        position: Int
    ) {
        binding.txtTitle.text = "Registro #${position + 1}"
    }
}