package com.functrco.sail.repository

import android.util.Log
import com.functrco.sail.models.CategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CategoriesRepository {

    suspend fun getAll(): List<CategoryModel> {
        val categories = mutableListOf<CategoryModel>()
        dbReference.get().addOnSuccessListener {
            it.children.forEach { dataSnapshot ->
                dataSnapshot.getValue<CategoryModel>()?.let { category ->
                    categories.add(category)
                }
            }
        }.addOnFailureListener {
            Log.d(TAG, "getAll() failed", it.cause)
        }.await()

        Log.d(TAG,  categories.toString())

        return categories
    }


    suspend fun get(categoryId: String) = suspendCoroutine<CategoryModel> { continuation ->
        val valueEventListener =
            dbReference.child(categoryId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.getValue<CategoryModel>()?.let {
                        continuation.resume(it)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(Companion.TAG, "getAll:onCancelled", databaseError.toException())
                    continuation.resume(CategoryModel())
                }
            })

        dbReference.child(categoryId).addValueEventListener(valueEventListener)
    }


    suspend fun insert(category: CategoryModel): String? {
        val key = dbReference.push().key

        if (key != null) {
            try {
                dbReference.child(key).setValue(category).await()
            } catch (e: Exception) {
                Log.w(TAG, "category insert unsuccessful", e.cause)
            }
        }
        return key
    }

    companion object {
        private val TAG = CategoriesRepository::class.java.name

        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.CATEGORIES)
    }
}