package com.functrco.sail.firebase.firebase_repository

import android.util.Log
import com.functrco.sail.models.ProductModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ProductsRepository {

    suspend fun insert(product: ProductModel): String? {
        val key = dbReference.push().key

        if (key != null) {
            try {
                dbReference.child(key).setValue(product).await()
            } catch (e: Exception) {
                Log.w(TAG, "product insert unsuccessful", e.cause)
            }
        }
        return key
    }

    companion object {
        private val TAG = ProductsRepository::class.java.name
        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.PRODUCTS)
    }
}