package com.functrco.sail.screens.main.cart

import com.functrco.sail.screens.main.products.ProductModel

data class CartItemModel(
    var product: ProductModel,
    var quantity: Int = 1
)