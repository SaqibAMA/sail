package com.functrco.sail.screens.main.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    private var carts = MutableLiveData<List<CartModel>>()

    fun getObserver() = carts

    /* TODO: replace the code to fetch carts from API */
    fun fetchCarts(): MutableLiveData<List<CartModel>>{
        carts.postValue(SampleCarts.getAll())
        return carts
    }

}




