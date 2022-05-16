package com.functrco.sail.sample_data

import com.functrco.sail.models.ProductsModel

object SampleProductsParents {
    fun getAll(): List<ProductsModel> {
        val products = SampleProducts.getAll()
        return listOf(
            ProductsModel("Popular Products", products.take(1)),
            ProductsModel("Feature Products", products.takeLast(2))
        )
    }
}