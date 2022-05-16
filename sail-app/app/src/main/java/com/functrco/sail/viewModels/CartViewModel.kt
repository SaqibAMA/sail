package com.functrco.sail.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.functrco.sail.models.CartModel
import com.functrco.sail.sample_data.SampleCarts

class CartViewModel : ViewModel() {

    private var cart = MutableLiveData<CartModel>()

    fun getObserver() = cart

    /* TODO: replace the code to fetch carts from API */
    fun fetchCarts(): MutableLiveData<CartModel>{
        cart.postValue(SampleCarts.get())
        return cart
    }

}




