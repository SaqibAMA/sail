package com.functrco.sail.screens.main.home.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {

    private var products = MutableLiveData<List<Product>>()

    fun getObserver() = products

    /* TODO: replace the code to fetch products from API */
    fun fetchProducts(): MutableLiveData<List<Product>>{
        products.postValue(SampleProductData.getAll())
        return products
    }

}




