package com.spcswabapp.content.form_anexoo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spcswabapp.content.form_anexoo.fragments.*

class ViewPagerAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FormAnexoORegisterDataFragment()
            1 -> RevisionesDiariasFragment()
            else -> FormAnexoORegisterDataFragment()
        }
    }

}