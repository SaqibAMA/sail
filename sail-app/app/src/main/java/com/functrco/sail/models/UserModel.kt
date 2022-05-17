package com.functrco.sail.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel(
    var name: String? = "",
    var phoneNum: String? = "",
    var email: String? = "",
    var imageUrl: String? = "",
    var address: String? = "",
    @set:Exclude @get:Exclude var id: String? = null
)