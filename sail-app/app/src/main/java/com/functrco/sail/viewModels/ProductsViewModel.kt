package com.functrco.sail.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.functrco.sail.R
import com.functrco.sail.firebase.repository.ProductsParentsRepository
import com.functrco.sail.firebase.repository.ProductsRepository
import com.functrco.sail.firebase.repository.ReviewsRepository
import com.functrco.sail.firebase.storage.FirebaseStorageDirectories
import com.functrco.sail.firebase.storage.FirebaseStorageManager
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ProductsModel
import com.functrco.sail.models.ReviewModel
import com.functrco.sail.sample_data.SampleProductsParents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(app: Application) : AndroidViewModel(app) {
    private var productsParents = MutableLiveData<List<ProductsModel>>()
    fun getObserver() = productsParents

    /* TODO: replace the code to fetch products parents from API */
    fun getAll(): MutableLiveData<List<ProductsModel>> {

        viewModelScope.launch {
            productsParents.postValue(ProductsParentsRepository().getAllNested())
        }
        return productsParents
    }


    private suspend fun insert(product: ProductModel): String? {
        // upload image and get uri
        val imageUri = FirebaseStorageManager.uploadImage(
            product.imageResourceId ?: R.drawable.default_product_img,
            FirebaseStorageDirectories.PRODUCTS,
            getApplication<Application>().resources
        )
        product.imageUrl = imageUri

        val reviewsId = mutableListOf<String>()
        // insert reviews
        product.reviews?.forEach { review ->
            val key = ReviewsRepository().insert(review)
            if(key != null){
                reviewsId.add(key)
            }
        }
        product.reviewsId = reviewsId
        return ProductsRepository().insert(product)
    }


    private suspend fun insert(productParent: ProductsModel): String? {

        // insert products
        val productsId = mutableListOf<String>()
        productParent.products?.forEach { product ->
            val k = insert(product)
            if (k != null) {
                productsId.add(k)
            }
            productParent.productsId = productsId.toList()
        }

        return ProductsParentsRepository().insert(productParent)
    }

    suspend fun init() {
        val sampleProducts = SampleProductsParents.getAll()
        sampleProducts.forEach { productsParent ->
            insert(productsParent)
        }
    }

    companion object {
        private val TAG = ProductsViewModel::class.java.name
    }
}




