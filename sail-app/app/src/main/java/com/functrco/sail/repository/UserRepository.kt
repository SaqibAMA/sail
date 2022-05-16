package com.functrco.sail.repository

import android.annotation.SuppressLint
import android.util.Log
import com.firebase.ui.auth.data.model.User
import com.functrco.sail.models.CategoryModel
import com.functrco.sail.models.UserModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserRepository {
    suspend fun getAll(): List<UserModel> {
        val users = mutableListOf<UserModel>()
        dbReference.get().addOnSuccessListener {
            it.children.forEach { dataSnapshot ->
                dataSnapshot.getValue<UserModel>()?.let { user ->
                    users.add(user)
                }
            }
            Log.d(TAG, it.toString())
        }.addOnFailureListener {
            Log.d(TAG, "getAll() failed", it.cause)
        }.await()

        Log.d(TAG, users.toString())

        return users
    }

    suspend fun insert(key: String, user: UserModel): String {
        try {
            dbReference.child(key).setValue(user).await()
        } catch (e: Exception) {
            Log.w(TAG, "user insert unsuccessful", e.cause)
        }
        return key
    }


    companion object {
        private val TAG = UserRepository::class.java.name

        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.USERS)
    }
}