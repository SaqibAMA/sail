package com.functrco.sail.screens.main.cart

import com.functrco.sail.screens.main.products.ProductModel

data class CartModel (
    var cartItems: MutableList<CartItemModel> = mutableListOf()
)