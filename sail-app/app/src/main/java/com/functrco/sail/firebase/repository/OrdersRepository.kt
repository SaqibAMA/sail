package com.functrco.sail.firebase.repository

import android.util.Log
import com.functrco.sail.models.CategoryModel
import com.functrco.sail.models.OrderModel
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ReviewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class OrdersRepository {

    // to insert a new order in the database
    suspend fun insert(order: OrderModel): String? {
        val key = dbReference.push().key

        if (key != null) {
            try {
                dbReference.child(key).setValue(order).await()
            } catch (e: Exception) {
                Log.w(TAG, "order insert unsuccessful", e.cause)
            }
        }
        return key
    }

    // update order isReviewed flag
    suspend fun updateIsOrderReviewed(orderId: String, isReviewed: Boolean) {
        dbReference.child(orderId).child("reviewed").setValue(isReviewed).await()
    }


    // get count of orders of a specific product
    suspend fun countProductOrders(productId: String?): Int {
        var ordersCount = 0
        dbReference.get().addOnSuccessListener {
            it.children.forEach { ds ->
                Log.d(TAG, ds.toString())
                ds.getValue<OrderModel>()?.let { order ->
                    if (order.productId == productId) {
                        ordersCount++
                    }
                }
            }
        }.addOnFailureListener {
            Log.w(TAG, "get order count unsuccessful", it.cause)

        }.await()

        Log.d(TAG, ordersCount.toString())
        return ordersCount
    }

// to insert a list of orders
suspend fun insertAll(orders: List<OrderModel>) {
    orders.forEach { order ->
        insert(order)
    }
}

// fetch nested details of a order
suspend fun get(orderId: String?): OrderModel? {
    var order: OrderModel? = null
    if (orderId != null) {
        dbReference.child(orderId).get().addOnSuccessListener {
            order = it.getValue<OrderModel>()
            order?.id = it.key
        }.addOnFailureListener {
            Log.d(TAG, "get failed", it.cause)
        }.await()
    }
    
    Log.d(TAG, order.toString())
    return order
}


// fetch nested details of a order
private suspend fun getNestedDetails(order: OrderModel?): OrderModel? {
    if (order != null) {
        val getProduct: suspend (String) -> ProductModel? = { productId ->
            ProductsRepository().getNested(productId)
        }
        // get order product
        if (order.productId != null) {
            order.product = getProduct(order.productId!!)
        }
    }
    return order
}


// to get a list of orders
suspend fun getAll(userId: String): List<OrderModel> {
    val orders = mutableListOf<OrderModel>()
    dbReference.get().addOnSuccessListener {
        it.children.forEach { dataSnapshot ->
            dataSnapshot.getValue<OrderModel>().let { order ->
                if (order != null && order.userId == userId) {
                    order.id = dataSnapshot.key
                    orders.add(order)
                }
            }
        }
    }.addOnFailureListener {
        Log.d(TAG, "getAll() failed", it.cause)
    }.await()

    for (i in orders.indices) {
        val order = getNestedDetails(orders[i])
        if (order != null) {
            orders[i] = order
        }
    }

    Log.d(TAG, orders.toString())
    return orders
}


companion object {
    private val TAG = OrdersRepository::class.java.name
    private val dbReference =
        FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.ORDERS)
}
}