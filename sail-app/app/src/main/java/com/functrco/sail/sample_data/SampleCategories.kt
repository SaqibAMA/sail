package com.functrco.sail.sample_data

import com.functrco.sail.R
import com.functrco.sail.models.CategoryModel

object SampleCategories {

    fun getAll(): List<Pair<String, Int>> {
        return mutableListOf(
            Pair("Shoes", R.drawable.default_product_img),
            Pair("Headphone", R.drawable.img_headphone_product),
            Pair("Phone", R.drawable.img_phone_product),
        )
    }

}