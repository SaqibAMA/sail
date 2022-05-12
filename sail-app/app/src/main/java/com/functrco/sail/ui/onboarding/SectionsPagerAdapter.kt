package com.functrco.sail.ui.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Onboarding Page #$position"
    }

    override fun getCount(): Int {
        return 3
    }
}