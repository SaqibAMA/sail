package com.functrco.sail.sample_data
import com.functrco.sail.models.CartItemModel
import com.functrco.sail.models.CartModel

object SampleCarts {
    fun get(): CartModel {
        val products = SampleProducts.getAll()
        val cart = CartModel()
        products.forEach{
            cart.cartItems!!.add(CartItemModel(it, 1))
        }
        return cart
    }
}