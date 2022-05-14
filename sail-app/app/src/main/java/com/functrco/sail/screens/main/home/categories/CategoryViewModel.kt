package com.functrco.sail.screens.main.home.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    private var categories = MutableLiveData<List<CategoryModel>>()

    fun getObserver() = categories

    /* TODO: replace the code to fetch categories from API */
    fun fetchCategories(): MutableLiveData<List<CategoryModel>>{
        categories.postValue(SampleCategories.getAll())
        return categories
    }

}




