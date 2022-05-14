package com.functrco.sail.screens.main.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    private var cart = MutableLiveData<CartModel>()

    fun getObserver() = cart

    /* TODO: replace the code to fetch carts from API */
    fun fetchCarts(): MutableLiveData<CartModel>{
        cart.postValue(SampleCarts.get())
        return cart
    }

}




