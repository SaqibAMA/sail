package com.functrco.sail.screens.main.cart

import com.functrco.sail.screens.main.products.SampleProducts

object SampleCarts {
    fun getAll(): List<CartModel> {
        val products = SampleProducts.getAll()
        val orders = mutableListOf<CartModel>()
        products.forEach{
            orders.add(CartModel(it, 2))
        }

        return orders
    }

}