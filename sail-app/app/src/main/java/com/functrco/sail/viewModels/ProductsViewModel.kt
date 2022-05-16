package com.functrco.sail.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.functrco.sail.R
import com.functrco.sail.firebase.firebase_storage.FirebaseStorageDirectories
import com.functrco.sail.firebase.firebase_storage.FirebaseStorageManager
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ProductsModel
import com.functrco.sail.firebase.firebase_repository.ProductsParentsRepository
import com.functrco.sail.firebase.firebase_repository.ProductsRepository
import com.functrco.sail.sample_data.SampleProductsParents
import kotlinx.coroutines.*

class ProductsViewModel(app: Application) : AndroidViewModel(app) {
    private var productsParents = MutableLiveData<List<ProductsModel>>()
    fun getObserver() = productsParents

    /* TODO: replace the code to fetch products parents from API */
    fun fetchProductsParents(): MutableLiveData<List<ProductsModel>> {
        productsParents.postValue(SampleProductsParents.getAll())
        return productsParents
    }

    private fun insert(product: ProductModel): String? {
        var key: String? = null

        // upload image and get uri
        runBlocking {
            val imageUri = FirebaseStorageManager.uploadImage(
                product.imageResourceId ?: R.drawable.default_product_img,
                FirebaseStorageDirectories.PRODUCTS,
                getApplication<Application>().resources
            )
            Log.d(TAG, "ImageUrl: $imageUri")
            product.imageUrl = imageUri
            key = ProductsRepository().insert(product)
        }

        return key
    }


    private fun insert(productParent: ProductsModel): String? {

        // insert products
        val productsId = mutableListOf<String>()
        productParent.products?.forEach { product ->

            val key = runBlocking {
                insert(product)
            }
            Log.d(TAG, "Key: ${key ?: "null key"}")
            if (key != null) {
                productsId.add(key)
            }
        }
        productParent.productsId = productsId.toList()
        return runBlocking {
            ProductsParentsRepository().insert(productParent)
        }
    }

    fun init() {
        val sampleProducts = SampleProductsParents.getAll()
        sampleProducts.forEach { productsParent ->
            viewModelScope.launch {
                insert(productsParent)
            }
        }
    }

    companion object {
        private val TAG = ProductsViewModel::class.java.name
    }
}




