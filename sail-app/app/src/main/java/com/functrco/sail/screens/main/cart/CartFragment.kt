package com.functrco.sail.screens.main.cart

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
import com.functrco.sail.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel

    private val cartsAdaptor = CartAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        setCarts()
        observeCarts()

        return binding.root
    }


    // bind cart to the recycler view
    private fun setCarts(){
        binding.cartsRecyclerView.adapter = cartsAdaptor
        binding.cartsRecyclerView.layoutManager = LinearLayoutManager(this.context)

        cartsAdaptor.onItemClick = {
            val redirectToCartPage = Intent(activity, ProductPage::class.java)
            // TODO: pass cart information through intent
            startActivity(redirectToCartPage)
        }
    }

    // set observer on the carts list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeCarts() {
        cartViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cartsAdaptor.setListData(it)
                cartsAdaptor.notifyDataSetChanged()
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