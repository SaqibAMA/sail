package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CartItemModel(
    var productId: String? = "",
    @set:Exclude @get:Exclude var product: ProductModel? = null,
    var quantity: Int = 1,
    @set:Exclude @get:Exclude var id: String? = null
)