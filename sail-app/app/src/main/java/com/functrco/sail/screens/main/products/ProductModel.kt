package com.functrco.sail.screens.main.products

import android.accounts.AuthenticatorDescription

data class ProductModel(
    val name: String,
    val price: Float = 0F,
    val description: String = "",
    val imageId: Int? = null
)

