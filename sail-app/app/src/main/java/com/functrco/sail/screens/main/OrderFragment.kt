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
import com.functrco.sail.MainActivity
import com.functrco.sail.ProductPage
import com.functrco.sail.adaptors.OrderAdaptor
import com.functrco.sail.databinding.FragmentOrdersBinding
import com.functrco.sail.utils.Util
import com.functrco.sail.viewModels.OrderViewModel

class OrderFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel

    private val ordersAdaptor = OrderAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Util.removeFragment(activity?.supportFragmentManager, MainActivity.CART_FRAGMENT_TAG)


        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]


        setOrders()
        observeOrders()

        return binding.root
    }


    // bind order to the recycler view
    private fun setOrders(){
        binding.ordersRecyclerView.adapter = ordersAdaptor
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(this.context)

        ordersAdaptor.onItemClick = {
            val redirectToOrderPage = Intent(activity, ProductPage::class.java)
            // TODO: pass order information through intent
            startActivity(redirectToOrderPage)
        }
    }

    // set observer on the orders list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeOrders() {
        orderViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                ordersAdaptor.setListData(it)
                ordersAdaptor.notifyDataSetChanged()
            } else {
                Log.d(TAG, "observeOrders(): null")
            }
        })
        orderViewModel.fetchOrders()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "OrderFragment"
    }
}