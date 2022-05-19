package com.functrco.sail.models

import com.google.firebase.database.Exclude
import java.io.Serializable

data class ReviewModel(
    var userId: String? = null,
    @set:Exclude @get:Exclude var user: UserModel? = null,
    var text: String? = null,
    var rating: Float? = null,
    var date: String? = null,
    @set:Exclude @get:Exclude var id: String? = null
)