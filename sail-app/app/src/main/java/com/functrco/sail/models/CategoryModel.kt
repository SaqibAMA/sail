package com.functrco.sail.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CategoryModel(
    var name: String? = null,
    var imageUrl: String? = null
)