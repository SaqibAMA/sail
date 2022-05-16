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
import com.functrco.sail.viewModels.CartViewModel

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel

    private val cartAdaptor = CartAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        setCart()
        observeCart()

        return binding.root
    }


    // bind cart to the recycler view
    private fun setCart(){
        binding.cartRecyclerView.adapter = cartAdaptor
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this.context)

        cartAdaptor.onItemClick = {
            val redirectToCartPage = Intent(activity, ProductPage::class.java)
            // TODO: pass cart information through intent
            startActivity(redirectToCartPage)
        }
    }

    // set observer on the carts list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeCart() {
        cartViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cartAdaptor.setListData(it.cartItems!!)
                cartAdaptor.notifyDataSetChanged()
            } else {
                Log.d(TAG, "observeCarts(): null")
            }
        })
        cartViewModel.fetchCarts()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "CartFragment"
    }
}