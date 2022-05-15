package com.functrco.sail.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CartItemModel(
    var product: ProductModel? = null,
    var quantity: Int = 1
)