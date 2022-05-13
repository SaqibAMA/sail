package com.functrco.sail.screens.main.home.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel : ViewModel() {

    private var products = MutableLiveData<List<Product>>()

    fun getObserver() = products

    /* TODO: replace the code to fetch products from API */
    fun fetchProducts(): MutableLiveData<List<Product>>{
        products.postValue(SampleProducts.getAll())
        return products
    }

}




