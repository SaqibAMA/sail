package com.functrco.sail.screens.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.functrco.sail.R
import com.functrco.sail.adaptors.CartAdaptor
import com.functrco.sail.databinding.FragmentCartBinding
import com.functrco.sail.firebase.repository.CartRepository
import com.functrco.sail.firebase.repository.OrdersRepository
import com.functrco.sail.models.OrderModel
import com.functrco.sail.utils.Util
import com.functrco.sail.viewModels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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
                        30
                    )
                )
            }
            GlobalScope.launch {
                // insert each cart Items as an order
                OrdersRepository().insertAll(orders)

                // delete cart
                CartRepository().delete(user?.uid!!)

                // navigate to the order page
                performNoBackStackTransaction("order_fragment", OrderFragment())
            }
        }
    }

    private fun performNoBackStackTransaction(tag: String, fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment, tag)
            ?.commit()
    }



    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Here hello")
        if (user != null) {
            Log.d(TAG, cartAdaptor.carts.size.toString())
            GlobalScope.launch {
                cartViewModel.insert(user!!.uid, cartViewModel.getObserver().value)
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

        if (user != null) {
            cartViewModel.getAll(user!!.uid)
        }
    }


    // update total payment of the cart in ui
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