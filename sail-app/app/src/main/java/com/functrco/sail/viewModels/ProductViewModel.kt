package com.functrco.sail.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.functrco.sail.models.ProductModel
import com.functrco.sail.sample_data.SampleProducts

class ProductViewModel (app: Application) : AndroidViewModel(app)  {

    private var products = MutableLiveData<List<ProductModel>>()

    fun getObserver() = products

    /* TODO: replace the code to fetch products from API */
    fun fetchProducts(): MutableLiveData<List<ProductModel>>{
        products.postValue(SampleProducts.getAll())
        return products
    }

    companion object {
        private val TAG = ProductViewModel::class.java.name
    }
}




