package com.functrco.sail.screens.main.home.products_parent

import com.functrco.sail.models.ProductsModel
import com.functrco.sail.screens.main.products.SampleProducts

object SampleProductsParents {
    fun getAll(): List<ProductsModel> {
        val products = SampleProducts.getAll()

        return listOf<ProductsModel>(
            ProductsModel("Popular Products", products.take(5)),
            ProductsModel("Feature Products", products.takeLast(5))
        )
    }

}