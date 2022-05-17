package com.functrco.sail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.functrco.sail.databinding.ActivityMainBinding
import com.functrco.sail.models.ProductModel
import com.functrco.sail.screens.main.CartFragment
import com.functrco.sail.utils.Util
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var product: ProductModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // view binding implementation
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainer)
        binding.bottomNavbar.setupWithNavController(navController)


        // get product information from the intent
        product = Gson().fromJson(
            intent?.getStringExtra("product_info"),
            ProductModel::class.java
        )
        navigateToCartPage()
    }

    private fun navigateToCartPage() {
        if (product != null) {
            val bundle = Bundle()
            bundle.putString("product_info", Util.toSerializable(product))
            val cartFragment = CartFragment()
            cartFragment.arguments = bundle
            performNoBackStackTransaction(CART_FRAGMENT_TAG, cartFragment)
        } else {
            Log.d(TAG, "null product")
        }
    }


    private fun performNoBackStackTransaction(
        tag: String,
        fragment: Fragment
    ) {
        binding.bottomNavbar.menu.getItem(2).isChecked = true
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }


    companion object {
        private val TAG = MainActivity::class.java.name
        const val CART_FRAGMENT_TAG = "cart_fragment"
    }

}