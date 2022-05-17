package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CartModel(
    var cartItems: List<CartItemModel>? = null,
    @set:Exclude @get:Exclude var id: String? = null
)