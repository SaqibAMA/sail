package com.functrco.sail.screens.main.home.categories

object SampleCategories {

    fun getAll(): List<CategoryModel> {
        return mutableListOf(
            CategoryModel("Mouse"),
            CategoryModel("Keyboard"),
            CategoryModel("Phone"),
            CategoryModel("Shoes"),
            CategoryModel("Airpods"),
            CategoryModel("Keyboard")
        )
    }

}