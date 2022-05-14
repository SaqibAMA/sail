package com.functrco.sail.screens.main.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrdersViewModel : ViewModel() {

    private var orders = MutableLiveData<List<Order>>()

    fun getObserver() = orders

    /* TODO: replace the code to fetch orders from API */
    fun fetchOrders(): MutableLiveData<List<Order>>{
        orders.postValue(SampleOrders.getAll())
        return orders
    }

}




