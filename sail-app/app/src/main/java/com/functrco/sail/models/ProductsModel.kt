package com.functrco.sail.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductsModel(
    var title: String? = "",
    var productsParent: List<ProductModel>? = null
)