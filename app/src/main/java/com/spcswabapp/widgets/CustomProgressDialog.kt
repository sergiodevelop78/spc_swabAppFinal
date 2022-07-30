package com.spcswabapp.widgets

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spcswabapp.databinding.CustomProgressLayoutBinding
import com.spcswabapp.utils.getActivity

class CustomProgressDialog(private val context: Context) {
    private var viewGroup : ViewGroup? = null
    private lateinit var binding : CustomProgressLayoutBinding
    var message : String = ""

    fun show(){
        viewGroup = getActivity(context)?.window?.decorView?.rootView as ViewGroup?
        binding = CustomProgressLayoutBinding.inflate(LayoutInflater.from(context))
        binding.txtMessage.text = message
        viewGroup?.addView(binding.root)
    }

    fun hide(){
        if(viewGroup != null){
            viewGroup?.removeView(binding.root)
        }
    }
}