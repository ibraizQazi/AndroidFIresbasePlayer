package com.ibraiz.firebaseplayerapp.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.ViewGroup
import android.util.SparseArray
import android.support.v4.app.FragmentStatePagerAdapter


abstract class SmartFragmentStatePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    // Sparse array to keep track of registered fragments in memory
    private val registeredFragments = SparseArray<Fragment>()

    // Register the fragment when the item is instantiated
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    // Unregister when the item is inactive
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

    fun fetchFragmentByPosition(pagerInstance: ViewGroup, position: Int): Fragment {
        val existingInstance = registeredFragments.get(position)
        return existingInstance ?: instantiateItem(pagerInstance, position) as Fragment
    }
}