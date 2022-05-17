package com.functrco.sail.screens.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.functrco.sail.ProductPage
import com.functrco.sail.adaptors.CartAdaptor
import com.functrco.sail.databinding.FragmentCartBinding
import com.functrco.sail.models.CartItemModel
import com.functrco.sail.models.ProductModel
import com.functrco.sail.utils.Util
import com.functrco.sail.viewModels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel
    private val cartAdaptor = CartAdaptor()

    private var user:FirebaseUser? = null
    private var product: ProductModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        user = FirebaseAuth.getInstance().currentUser

        // get product information from the intent
        product = Gson().fromJson(
            arguments?.getString("product_info"),
            ProductModel::class.java
        )

        initCart()
        setCart()
        observeCart()

        return binding.root
    }



    private fun initCart() {
        if(product != null){
             Log.d(TAG, "Product: " +  product.toString())
            // remove previous fragment i.e home fragment
            val fragment = activity?.supportFragmentManager?.findFragmentById(0)
            if (fragment != null) activity?.supportFragmentManager?.beginTransaction()?.remove(fragment)?.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Here hello")
        if(user != null) {
            Log.d(TAG, cartAdaptor.carts.size.toString())
            GlobalScope.launch {
                cartViewModel.insert(user!!.uid, cartViewModel.getObserver().value)
            }
        }

    }

    // bind cart to the recycler view
    private fun setCart(){
        binding.cartRecyclerView.adapter = cartAdaptor
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this.context)

        cartAdaptor.onPlusClick = {
            it.quantity++
            cartAdaptor.notifyDataSetChanged()
            updateTotalPaymentText()
        }
        cartAdaptor.onMinusClick = {
            if(it.quantity > 1) it.quantity--
            cartAdaptor.notifyDataSetChanged()
            updateTotalPaymentText()

        }
    }

    // set observer on the carts list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeCart() {
        cartViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it.toString())
            if (it?.cartItems != null) {
                cartAdaptor.setListData(it.cartItems!!)
                cartAdaptor.notifyDataSetChanged()
                updateTotalPaymentText()
            } else {
                Log.d(TAG, "observeCarts(): null")
            }
        })

        if(user != null){
            GlobalScope.launch {
                if(product != null)
                    cartViewModel.insert(user?.uid!!, CartItemModel(product?.id, product))
                cartViewModel.getAll(user!!.uid)
            }
        }
    }


    private fun updateTotalPaymentText() {
        cartViewModel.getObserver().value.let {
            binding.cartTotalPaymentTextView.text = Util.toCurrency(it?.let { it1 ->
                Util.calculateTotalPayment(
                    it1
                )
            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "CartFragment"
    }
}