package com.functrco.sail.firebase.repository

import android.util.Log
import com.functrco.sail.models.CategoryModel
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ProductsModel
import com.functrco.sail.models.ReviewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
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


    // add a product review, update product's reviews list and return the review id
    suspend fun addReview(productId: String, review: ReviewModel): String? {
        val reviewId = ReviewsRepository().insert(review)
        if (reviewId != null) {
            val reviewsId = mutableListOf<String>()
            val reviewsRef = dbReference.child(productId).child("reviewsId")
            reviewsRef.get().addOnSuccessListener {
                it.children.forEach { ds ->
                    ds.getValue<String>()?.let { id ->
                        reviewsId.add(id)
                    }
                }
            }
                .addOnFailureListener { e ->
                    Log.w(TAG, "add review unsuccessful", e.cause)

                }
                .await()

            reviewsId.add(reviewId)
            reviewsRef.setValue(reviewsId).await()
        }
        return reviewId
    }


    // fetch nested details of a product
    private suspend fun getNestedDetails(product: ProductModel?): ProductModel? {
        if (product != null) {
            val getCategory: suspend (String) -> CategoryModel? = { categoryId ->
                CategoriesRepository().get(categoryId)
            }

            // get product category
            if (product.categoryId != null) {
                product.category = getCategory(product.categoryId!!)
            }

            // get product reviews
            if (product.reviewsId != null) {
                val getReview: suspend (String) -> ReviewModel? = { reviewId ->
                    ReviewsRepository().getNested(reviewId)
                }

                val reviews = mutableListOf<ReviewModel>()
                product.reviewsId!!.forEach { reviewId ->
                    val review = getReview(reviewId)
                    if (review != null) {
                        reviews.add(review)
                    }
                }
                product.reviews = reviews
            }
        }
        return product
    }


    // return a specific product with nested details
    suspend fun getNested(productId: String): ProductModel? {
        val product: ProductModel? = get(productId)
        return getNestedDetails(product)
    }

    // return a specific product
    suspend fun get(productId: String): ProductModel? {
        var product: ProductModel? = null

        dbReference.child(productId).get()
            .addOnSuccessListener {
                product = it.getValue<ProductModel>()
                product?.id = it.key
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "getAll:onFailure", e.cause)
            }.await()

        return product
    }

    suspend fun getAll(): List<ProductModel> {
        val products = mutableListOf<ProductModel>()

        dbReference.get().addOnSuccessListener {
            it.children.forEach { dataSnapshot ->
                dataSnapshot.getValue<ProductModel>().let {
                    if (it != null) {
                        it.id = dataSnapshot.key
                        products.add(it)
                    }
                }
            }
        }.addOnFailureListener {
            Log.d(TAG, "getAll() failed", it.cause)
        }.await()

        for (i in products.indices) {
            val product = getNestedDetails(products[i])
            if (product != null) {
                product.ordersCount = OrdersRepository().countProductOrders(product.id)
                products[i] = product
            }
        }

        Log.d(TAG, products.toString())
        return products
    }


    companion object {
        private val TAG = ProductsRepository::class.java.name
        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.PRODUCTS)
    }
}