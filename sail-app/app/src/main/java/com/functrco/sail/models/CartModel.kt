package com.functrco.sail.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CartModel (
    var cartItems: MutableList<CartItemModel>? = null
)