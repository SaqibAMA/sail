package com.functrco.sail.screens.main.orders

import com.functrco.sail.screens.main.products.ProductModel

data class OrderModel (val product: ProductModel, val status: String)