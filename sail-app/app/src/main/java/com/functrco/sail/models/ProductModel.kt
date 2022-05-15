package com.functrco.sail.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductModel(
    var name: String? = "",
    var price: Float? = 0F,
    var description: String? = "",
    var imageId: Int? = null
)

