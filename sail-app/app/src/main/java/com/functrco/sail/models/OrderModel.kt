package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OrderModel (
    var productId: String? = "",
    @set:Exclude @get:Exclude var product: ProductModel? = null,
    var status: String? = "",
    var quantity: Int? = 1,
    var deliveryDays: Int? = 30,
    @set:Exclude @get:Exclude var id: String? = null
)