package com.functrco.sail.firebase.repository

import android.provider.Settings
import android.util.Log
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ProductsModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ProductsParentsRepository {
    suspend fun insert(productsParent: ProductsModel): String? {
        val key = dbReference.push().key
        if (key != null) {
            try {
                dbReference.child(key).setValue(productsParent).await()
            } catch (e: Exception) {
                Log.w(TAG, "products parent insert unsuccessful", e.cause)
            }
        }
        return key
    }

    suspend fun getAll(): List<ProductsModel> {
        val productsParents = mutableListOf<ProductsModel>()

        dbReference.get().addOnSuccessListener {
            it.children.forEach { dataSnapshot ->
                dataSnapshot.getValue<ProductsModel>()?.let { productsParent ->
                    productsParents.add(productsParent)
                }
            }
        }.addOnFailureListener {
            Log.d(TAG, "getAll() failed", it.cause)
        }.await()

        Log.d(TAG, productsParents.toString())
        return productsParents

    }


    suspend fun getAllNested(): List<ProductsModel> {

        val productsParents = getAll()
        productsParents.forEach { productsParent ->
            val products = mutableListOf<ProductModel>()
            val getProduct: suspend (String) -> ProductModel? = {
                ProductsRepository().getNested(it)
            }
            productsParent.productsId?.forEach { productId ->
                val product = getProduct(productId)
                if (product != null) {
                    products.add(product)
                }
            }
            productsParent.products = products
        }

        Log.d(TAG, productsParents.toString())
        return productsParents
    }

    companion object {
        private val TAG = ProductsParentsRepository::class.java.name
        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.PRODUCTS_PARENTS)
    }
}