package com.functrco.sail.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OrderModel (var product: ProductModel? = null, var status: String? = "")