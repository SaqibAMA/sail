package com.functrco.sail.screens.main.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private var orders = MutableLiveData<List<OrderModel>>()

    fun getObserver() = orders

    /* TODO: replace the code to fetch orders from API */
    fun fetchOrders(): MutableLiveData<List<OrderModel>>{
        orders.postValue(SampleOrders.getAll())
        return orders
    }

}




