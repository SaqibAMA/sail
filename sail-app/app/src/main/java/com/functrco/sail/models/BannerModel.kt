package com.functrco.sail.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("imageUrl")
    @Expose
    val imageUrl: String? = null
)
