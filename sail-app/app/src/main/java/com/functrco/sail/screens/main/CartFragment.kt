package com.functrco.sail.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.functrco.sail.R
import com.functrco.sail.adaptors.CartAdaptor
import com.functrco.sail.databinding.FragmentCartBinding
import com.functrco.sail.firebase.repository.CartRepository
import com.functrco.sail.firebase.repository.OrdersRepository
import com.functrco.sail.models.CartModel
import com.functrco.sail.models.OrderModel
import com.functrco.sail.utils.Util
import com.functrco.sail.viewModels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel
    private val cartAdaptor = CartAdaptor()

    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        user = FirebaseAuth.getInstance().currentUser

        binding.orderButton.setOnClickListener {
            handleMakeOrder()
        }
        setCart()
        observeCart()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (user != null) {
            cartViewModel.getAll(user!!.uid)
        }
    }

    // insert orders in the database, delete cart and redirect user to the order page
    private fun handleMakeOrder() {
        if (user?.uid != null) {
            val orders = mutableListOf<OrderModel>()
            cartAdaptor.carts.forEach { cartItem ->
                orders.add(
                    OrderModel(
                        user?.uid!!,
                        cartItem.productId,
                        cartItem.product,
                        "Progress",
                        cartItem.quantity,
                        Date().toString(),
                        30,
                        false
                    )
                )
            }

            if(orders.size > 0) {
                GlobalScope.launch {
                    // insert each cart Items as an order
                    OrdersRepository().insertAll(orders)

                    // delete cart
                    CartRepository().delete(user?.uid!!)

                    cartAdaptor.carts = mutableListOf()

                    withContext(Dispatchers.Main){
                        // navigate to the order page
                        findNavController().navigate(R.id.action_cartFragment_to_ordersFragment)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (user != null) {
            Log.d(TAG, cartAdaptor.carts.size.toString())
            GlobalScope.launch {
                cartViewModel.insert(user!!.uid, CartModel(cartAdaptor.carts))
            }
        }

    }

    // bind cart to the recycler view
    private fun setCart() {
        binding.cartRecyclerView.adapter = cartAdaptor
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this.context)

        cartAdaptor.onPlusClick = {
            it.quantity++
            cartAdaptor.notifyDataSetChanged()
            updateTotalPaymentText()
        }
        cartAdaptor.onMinusClick = {
            if (it.quantity > 1) it.quantity--
            cartAdaptor.notifyDataSetChanged()
            updateTotalPaymentText()
        }

        cartAdaptor.onDeleteItemClick = { cartItem, position ->

            GlobalScope.launch {
                if(user?.uid != null && cartItem.id != null){
                    // delete item from the cart database
                    CartRepository().delete(user?.uid!!, cartItem.id!!)

                    withContext(Dispatchers.Main) {
                        // delete item from the ui
                        cartAdaptor.carts.removeAt(position)
                        cartAdaptor.notifyItemRemoved(position)
                        updateTotalPaymentText()
                    }
                }
            }
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
    }


    // update total payment of the cart in ui
    private fun updateTotalPaymentText() {
        cartAdaptor.carts.let {
            binding.cartTotalPaymentTextView.text = Util.toCurrency(it.let { it1 ->
                Util.calculateTotalPayment(it1)
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