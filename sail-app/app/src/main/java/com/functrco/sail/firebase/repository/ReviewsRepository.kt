package com.functrco.sail.firebase.repository

import android.util.Log
import com.functrco.sail.models.ReviewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ReviewsRepository {

    suspend fun insert(review: ReviewModel): String? {
        val key = dbReference.push().key

        if (key != null) {
            try {
                dbReference.child(key).setValue(review).await()
            } catch (e: Exception) {
                Log.w(TAG, "review insert unsuccessful", e.cause)
            }
        }
        return key
    }

    suspend fun getNested(reviewId: String): ReviewModel? {
        val review: ReviewModel? = get(reviewId)

        if(review?.userId != null){
            review.user = UserRepository().get(review.userId!!)
        }

        Log.d(TAG, review.toString())
        return review
    }


    suspend fun get(reviewId: String): ReviewModel? {
        var review: ReviewModel? = null

        dbReference.child(reviewId).get()
            .addOnSuccessListener {
                review = it.getValue<ReviewModel>()
                review?.id = it.key
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "getAll:onFailure", e.cause)
            }.await()

        return review
    }

    companion object {
        private val TAG = ReviewsRepository::class.java.name
        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.REVIEWS)
    }
}