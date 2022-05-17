package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProductModel(
    var name: String? = "",
    var description: String? = "",
    var price: Float? = 0F,
    @set:Exclude @get:Exclude var imageResourceId: Int? = null,
    var imageUrl: String? = null,
    var brandName: String? = "",
    var categoryId: String? = "",
    var minQuantity: Int? = 100,
    @set:Exclude @get:Exclude var category: CategoryModel? = null,
    var reviewsId: List<String>? = null,
    @set:Exclude @get:Exclude var reviews: List<ReviewModel>? = null,
    @set:Exclude @get:Exclude var id: String? = null
)

