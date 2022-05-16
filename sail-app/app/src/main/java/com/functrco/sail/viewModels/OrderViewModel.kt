package com.functrco.sail.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.functrco.sail.models.OrderModel
import com.functrco.sail.sample_data.SampleOrders

class OrderViewModel : ViewModel() {

    private var orders = MutableLiveData<List<OrderModel>>()

    fun getObserver() = orders

    /* TODO: replace the code to fetch orders from API */
    fun fetchOrders(): MutableLiveData<List<OrderModel>>{
        orders.postValue(SampleOrders.getAll())
        return orders
    }

}




