package com.functrco.sail.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.functrco.sail.firebase.repository.OrdersRepository
import com.functrco.sail.models.OrderModel
import com.functrco.sail.sample_data.SampleOrders
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private var orders = MutableLiveData<List<OrderModel>>()

    fun getObserver() = orders

    fun getAll(userId: String): MutableLiveData<List<OrderModel>>{
        viewModelScope.launch {
            orders.postValue(OrdersRepository().getAll(userId))
        }
        return orders
    }

}




