package com.functrco.sail.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.functrco.sail.MainActivity
import com.functrco.sail.R
import com.functrco.sail.utils.Util

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Util.removeFragment(activity?.supportFragmentManager, MainActivity.CART_FRAGMENT_TAG)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}