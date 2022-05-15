package com.functrco.sail.screens.main.orders

import com.functrco.sail.models.OrderModel
import com.functrco.sail.screens.main.products.SampleProducts

object SampleOrders {
    fun getAll(): List<OrderModel> {
        val products = SampleProducts.getAll()
        val orders = mutableListOf<OrderModel>()
        products.forEach{
            orders.add(OrderModel(it, "completed"))
        }

        return orders
    }

}