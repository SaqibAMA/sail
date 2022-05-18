package com.functrco.sail

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
    private var goToCartPage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // view binding implementation
        setContentView(binding.root)

        // initialize Admob
        MobileAds.initialize(this) {}

        navController = findNavController(R.id.fragmentContainer)
        binding.bottomNavbar.setupWithNavController(navController)

        // get product information from the intent
        goToCartPage = intent.getBooleanExtra("go_to_cart_page", false)
        navigateToCartPage()
    }


    private fun navigateToCartPage() {
        if (goToCartPage) {
            val action = HomeFragmentDirections.actionHomeFragmentToCartFragment()
            navController.navigate(R.id.action_homeFragment_to_cartFragment)
        } else {
            Log.d(TAG, "do not go to cart page")
        }
    }


    companion object {
        private val TAG = MainActivity::class.java.name
    }

}