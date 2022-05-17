package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductsModel(
    var title: String? = "",
    @set:Exclude @get:Exclude var products: List<ProductModel>? = null,
    var productsId: List<String>? = null,
    @set:Exclude @get:Exclude var id: String? = null
)