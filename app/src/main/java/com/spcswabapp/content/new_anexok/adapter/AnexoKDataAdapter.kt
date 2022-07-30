package com.spcswabapp.content.new_anexok.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spcswabapp.R
import com.spcswabapp.content.new_anexok.entities.SwabTipsAnalsisRiesgoEntity
import com.spcswabapp.databinding.AnexokItemBinding
import com.spcswabapp.utils.disable


class AnexoKDataAdapter(private var anexokItemsList: MutableList<SwabTipsAnalsisRiesgoEntity>,
                        private val listener: OnItemClickListener, val idEstadoSwab: Int)
    : RecyclerView.Adapter<AnexoKDataAdapter.ViewHolder>() {

    var isSelectedAll : Boolean = false
    var list: List<AnexoKDataAdapter>? = null

    private var checkPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.anexok_item, parent, false)
        return ViewHolder(v)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nameText =
            anexokItemsList[position].idtips_analisisderiesgo.toString() +
                    " / " + anexokItemsList.get(position).tips_analisisderiesgo

        holder.txtTitle.text = nameText
        //holder.txtTitle.text = idEstadoSwab.toString()

        holder.binding.rgSiNo.setOnCheckedChangeListener(null);
        // Clear selection
        holder.binding.rgSiNo.clearCheck();

        if (anexokItemsList[position].estado==1) {
            holder.binding.rbGood.isChecked = true
            holder.binding.rbBad.isChecked = false
        }
        else if (anexokItemsList[position].estado==2) {
            holder.binding.rbGood.isChecked = false
            holder.binding.rbBad.isChecked = true
        }
        else if (anexokItemsList[position].estado==0 || anexokItemsList[position].estado==3) {
            holder.binding.rbGood.isChecked = false
            holder.binding.rbBad.isChecked = false
        }

        if (idEstadoSwab==3) { // estado Finalizado
            println("SERGIO -- estado finalizado $idEstadoSwab --disabling combos")
            holder.binding.rgSiNo.isEnabled = false
            holder.binding.rbGood.isEnabled = false
            holder.binding.rbBad.isEnabled = false
        }

        holder.binding.rbGood.setOnClickListener {
            checkPosition = position
            anexokItemsList[position].estado = 1
            println("Presionando GOOD pos #"+position.toString())
            holder.binding.rbGood.isChecked = true
            holder.binding.rbBad.isChecked = false
            notifyItemChanged(position)
            notifyDataSetChanged()
        }

        holder.binding.rbBad.setOnClickListener {
            checkPosition = position
            anexokItemsList[position].estado = 2
            println("Presionando BAD pos #"+position.toString())
            holder.binding.rbGood.isChecked = false
            holder.binding.rbBad.isChecked = true
            notifyItemChanged(position)
            notifyDataSetChanged()
        }

        holder.rbBad.setOnClickListener {
            anexokItemsList[position].estado = 2
            println("Presionado BAD pos #"+position.toString())
        }
    }

    override fun getItemCount(): Int = anexokItemsList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = AnexokItemBinding.bind(itemView)
        val txtTitle = binding.txtTitle
        val rbGood = binding.rbGood
        val rbBad = binding.rbBad

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}



