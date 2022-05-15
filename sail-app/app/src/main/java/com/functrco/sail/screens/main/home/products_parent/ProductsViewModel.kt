package com.functrco.sail.screens.main.home.products_parent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.functrco.sail.models.ProductsModel

class ProductsViewModel : ViewModel() {

    private var productsParents = MutableLiveData<List<ProductsModel>>()

    fun getObserver() = productsParents

    /* TODO: replace the code to fetch products parents from API */
    fun fetchProductsParents(): MutableLiveData<List<ProductsModel>>{
        productsParents.postValue(SampleProductsParents.getAll())
        return productsParents
    }

}




