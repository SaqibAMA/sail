package com.functrco.sail.screens.main.home.product

object SampleProductData {

    fun getAll(): List<Product> {
        return mutableListOf(
            Product("Mouse", 99.99.toFloat()),
            Product("Keyboard", 5.99.toFloat()),
            Product("Phone", 1.00.toFloat()),
            Product("Shoes", 15.49.toFloat()),
            Product("Airpods", 499.00.toFloat()),
            Product("Keyboard", 12.99.toFloat())
        )
    }

}