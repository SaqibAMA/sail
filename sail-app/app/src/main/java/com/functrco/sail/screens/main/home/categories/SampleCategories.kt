package com.functrco.sail.screens.main.home.categories

object SampleCategories {

    fun getAll(): List<Category> {
        return mutableListOf(
            Category("Mouse"),
            Category("Keyboard"),
            Category("Phone"),
            Category("Shoes"),
            Category("Airpods"),
            Category("Keyboard")
        )
    }

}