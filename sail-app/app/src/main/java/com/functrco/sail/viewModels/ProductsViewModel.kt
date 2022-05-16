package com.functrco.sail.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.functrco.sail.models.ProductsModel
import com.functrco.sail.sample_data.SampleProductsParents

class ProductsViewModel : ViewModel() {

    private var productsParents = MutableLiveData<List<ProductsModel>>()

    fun getObserver() = productsParents

    /* TODO: replace the code to fetch products parents from API */
    fun fetchProductsParents(): MutableLiveData<List<ProductsModel>>{
        productsParents.postValue(SampleProductsParents.getAll())
        return productsParents
    }

}




