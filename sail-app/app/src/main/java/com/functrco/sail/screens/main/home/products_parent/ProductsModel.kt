package com.functrco.sail.screens.main.home.products_parent

import com.functrco.sail.screens.main.products.ProductModel

data class ProductsModel(
    val title: String,
    val productsParent: List<ProductModel>
)