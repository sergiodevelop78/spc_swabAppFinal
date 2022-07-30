package com.spcswabapp.content.new_order.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spcswabapp.content.new_order.fragments.*

class ViewPagerAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RegisterDataFragment()
            1 -> FuelFragment()
            2 -> CriticalPointVerificationFragment()
            3 -> DayliConsumptionMaterialsFragment()
            else -> TankDispatchFragment()
        }
    }

}