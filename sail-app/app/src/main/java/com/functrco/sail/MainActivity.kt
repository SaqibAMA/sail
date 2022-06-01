package com.functrco.sail

import android.bluetooth.BluetoothAdapter
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.functrco.sail.databinding.ActivityMainBinding
import com.functrco.sail.models.ProductModel
import com.functrco.sail.receivers.BluetoothBroadcastReceiver
import com.functrco.sail.screens.main.CartFragment
import com.functrco.sail.screens.main.HomeFragmentDirections
import com.functrco.sail.utils.Util
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var bluetoothReceiver: BluetoothBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // view binding implementation
        setContentView(binding.root)

        // initialize Admob
        MobileAds.initialize(this) {}

        // register broadcast receiver
        registerBroadcastReceiver()


        navController = findNavController(R.id.fragmentContainer)
        binding.bottomNavbar.setupWithNavController(navController)

        // get product information from the intent
        navigateToFragment()
    }

    private fun registerBroadcastReceiver() {
        registerBluetoothBroadcastReceiver()
    }

    private fun registerBluetoothBroadcastReceiver() {
        bluetoothReceiver = BluetoothBroadcastReceiver()
        registerReceiver(bluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }


    private fun navigateToFragment() {
        if (intent.getBooleanExtra("go_to_cart_page", false)) {
            navController.navigate(R.id.action_homeFragment_to_cartFragment)
        } else if (intent.getBooleanExtra("go_to_order_page", false)) {
            navController.navigate(R.id.action_homeFragment_to_ordersFragment)
        } else {
            Log.d(TAG, "no fragment flag received")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcastReceivers()
    }

    private fun unregisterBroadcastReceivers() {
        unregisterReceiver(bluetoothReceiver)
    }


    companion object {
        private val TAG = MainActivity::class.java.name
    }

}