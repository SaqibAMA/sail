package com.functrco.sail.screens.main.home.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoriesViewModel : ViewModel() {

    private var categories = MutableLiveData<List<Category>>()

    fun getObserver() = categories

    /* TODO: replace the code to fetch categories from API */
    fun fetchCategories(): MutableLiveData<List<Category>>{
        categories.postValue(SampleCategories.getAll())
        return categories
    }

}




