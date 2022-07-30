package com.spcswabapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class FragmentViewBinding<VB : ViewBinding, VM : ViewModel> : Fragment() {

    val viewModel: VM by lazy { getViewModel(clazz = viewModelClass()) }
    private var _binding: ViewBinding? = null
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB get() = _binding!! as VB
    abstract fun bindingInflater(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        boolean: Boolean
    ): VB

    abstract fun initViews()

    abstract fun viewModelListeners()

    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<VM> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>).kotlin
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelListeners()
        initViews()
    }
}