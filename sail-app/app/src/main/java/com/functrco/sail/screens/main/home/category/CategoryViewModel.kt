package com.functrco.sail.screens.main.home.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    private var categories = MutableLiveData<List<Category>>()

    fun getObserver() = categories

    /* TODO: replace the code to fetch categories from API */
    fun fetchCategories(): MutableLiveData<List<Category>>{
        categories.postValue(SampleCategoryData.getAll())
        return categories
    }

}




