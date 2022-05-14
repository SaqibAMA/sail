package com.functrco.sail.screens.main.cart
import com.functrco.sail.screens.main.products.SampleProducts

object SampleCarts {
    fun get(): CartModel {
        val products = SampleProducts.getAll()
        val cart = CartModel()
        products.forEach{
            cart.cartItems.add(CartItemModel(it, 1))
        }
        return cart
    }
}