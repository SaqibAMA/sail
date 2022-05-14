package com.functrco.sail.screens.main.orders

import com.functrco.sail.screens.main.home.products.SampleProducts

object SampleOrders {
    fun getAll(): List<Order> {
        val products = SampleProducts.getAll()
        val orders = mutableListOf<Order>()
        products.forEach{
            orders.add(Order(it, "completed"))
        }

        return orders
    }

}