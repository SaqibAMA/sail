package com.functrco.sail.sample_data

import com.functrco.sail.models.ProductModel

object SampleProducts {
    fun getAll(): List<ProductModel> {
        return mutableListOf(
            ProductModel("Mouse", 99.99.toFloat()),
            ProductModel("Keyboard", 5.99.toFloat()),
            ProductModel("Phone", 2.99.toFloat()),
            ProductModel("Shoes", 15.49.toFloat()),
            ProductModel("Airpods", 499.00.toFloat()),
            ProductModel("Keyboard", 12.99.toFloat()),
            ProductModel("Mouse", 99.99.toFloat()),
            ProductModel("Keyboard", 5.99.toFloat()),
            ProductModel("Phone", 2.99.toFloat()),
            ProductModel("Shoes", 15.49.toFloat()),
            ProductModel("Airpods", 499.00.toFloat()),
            ProductModel("Mouse", 99.99.toFloat()),
            ProductModel("Keyboard", 5.99.toFloat()),
            ProductModel("Phone", 2.99.toFloat()),
            ProductModel("Shoes", 15.49.toFloat()),
            ProductModel("Airpods", 499.00.toFloat())
        )
    }

}