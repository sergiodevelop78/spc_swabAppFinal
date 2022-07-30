package com.spcswabapp.widgets

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.databinding.CustomAlertDialogLayoutBinding
import com.spcswabapp.utils.getActivity

class CustomSalirDialog(private val context: Context) {
    private var viewGroup : ViewGroup? = null
    private lateinit var binding : CustomAlertDialogLayoutBinding
    var message : String = ""
    var isShow : Boolean = false
    var isDelete : Boolean = false
    var onAcceptClickListener : (() -> Unit) ?= null

    fun show(){
        viewGroup = getActivity(context)?.window?.decorView?.rootView as ViewGroup?
        binding = CustomAlertDialogLayoutBinding.inflate(LayoutInflater.from(context))
        binding.txtMessage.text = message
        viewGroup?.addView(binding.root)
        isShow = true
        if(isDelete){
            binding.btnAccept.setBackgroundColor(Color.parseColor("#B30707"))
        }else{
            binding.btnAccept.setBackgroundColor(context.resources.getColor(android.R.color.holo_red_light))
        }
        listeners()
    }

    private fun listeners(){
        binding.apply {
            btnAccept.setOnClickListener {
                onAcceptClickListener?.invoke()
                hide()
            }

            btnCancel.setOnClickListener {
                hide()
            }
        }
    }

    fun hide(){
        if(viewGroup != null){
            isShow = false
            viewGroup?.removeView(binding.root)
        }
    }

}