package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CategoryModel(
    var name: String? = null,
    @set:Exclude @get:Exclude var imageResourceId: Int? = null,
    var imageUrl: String? = null,
    @set:Exclude @get:Exclude var id: String? = null
)