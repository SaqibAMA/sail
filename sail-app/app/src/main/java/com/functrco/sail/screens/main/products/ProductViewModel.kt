package com.functrco.sail.screens.main.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.functrco.sail.models.ProductModel

class ProductViewModel : ViewModel() {

    private var products = MutableLiveData<List<ProductModel>>()

    fun getObserver() = products

    /* TODO: replace the code to fetch products from API */
    fun fetchProducts(): MutableLiveData<List<ProductModel>>{
        products.postValue(SampleProducts.getAll())
        return products
    }

}




