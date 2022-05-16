package com.functrco.sail.firebase.firebase_repository

import android.util.Log
import com.functrco.sail.models.ProductsModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ProductsParentsRepository {
    suspend fun insert(productsParent: ProductsModel): String? {
        val key = dbReference.push().key
        if (key != null) {
            try {
                dbReference.child(key).setValue(productsParent).await()
//                for (i in productsParent.productsId!!.indices){
//                    dbReference.child(key).child(i.toString()).setValue(productsParent.productsId!![i])
//                }
            } catch (e: Exception) {
                Log.w(TAG, "products parent insert unsuccessful", e.cause)
            }
        }
        return key
    }

    companion object {
        private val TAG = ProductsParentsRepository::class.java.name
        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.PRODUCTS_PARENTS)
    }
}