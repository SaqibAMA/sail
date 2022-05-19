package com.functrco.sail.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.functrco.sail.firebase.repository.ProductsRepository
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ReviewModel
import com.functrco.sail.sample_data.SampleProducts
import kotlinx.coroutines.launch

class ProductViewModel (app: Application) : AndroidViewModel(app)  {

    private var products = MutableLiveData<List<ProductModel>>()

    fun getObserver() = products

    /* TODO: replace the code to fetch products from API */
    fun getAll(): MutableLiveData<List<ProductModel>>{
        viewModelScope.launch {
            products.postValue(ProductsRepository().getAll())
        }
        return products
    }

    companion object {
        private val TAG = ProductViewModel::class.java.name
    }
}




