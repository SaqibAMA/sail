package com.functrco.sail.sample_data

import com.functrco.sail.R
import com.functrco.sail.models.CategoryModel

object SampleCategories {

    fun getAll(): List<CategoryModel> {
        return mutableListOf(
            CategoryModel("Shoes", R.drawable.default_product_img),
            CategoryModel("Headphone", R.drawable.img_headphone_product),
            CategoryModel("Phone", R.drawable.img_phone_product),
        )
    }

}