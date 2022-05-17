package com.functrco.sail.firebase.repository

import android.util.Log
import com.functrco.sail.models.CartItemModel
import com.functrco.sail.models.CartModel
import com.functrco.sail.utils.Util
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CartRepository {
    suspend fun insert(userId: String, cart: CartModel): String {

        // get cart info from the database
        val dbCart = get(userId)

        // to insert an item in the cart
        val insertCartItem: suspend (CartItemModel) -> String? = { cartItem ->
            var key: String? = cartItem.id

            // if item is not present in the cart generate a key for it
            if (dbCart == null || Util.isItemExists(dbCart, cartItem) == false) {
                key = dbReference.child(userId).push().key
            }

            // insert/update the cart item
            if (key != null) {
                dbReference.child(userId).child(key).setValue(cartItem).await()
            }
            key
        }
        try {
            cart.cartItems?.forEach { cartItem ->
                insertCartItem(cartItem)
            }
        } catch (e: Exception) {
            Log.w(TAG, "cart insert unsuccessful", e.cause)
        }
        return userId
    }


    suspend fun get(userId: String): CartModel? {
        var cart: CartModel? = null
        val cartItems = mutableListOf<CartItemModel>()
        dbReference.child(userId).get()
            .addOnSuccessListener {
                cart = CartModel(id = it.key)
                it.children.forEach { ds ->
                    ds.getValue<CartItemModel>().let { cartItem ->
                        if (cartItem != null) {
                            cartItem.id = ds.key
                            cartItems.add(cartItem)
                        }
                    }
                }
                cart?.cartItems = cartItems
            }
            .addOnFailureListener {
                Log.d(TAG, "getAll() failed", it.cause)
            }.await()

        Log.d(TAG, cart.toString())
        return cart
    }

    private suspend fun getNestedDetails(cart: CartModel?): CartModel? {
        if (cart?.cartItems != null) {
            val cartItems = mutableListOf<CartItemModel>()
            cart.cartItems?.forEach { cartItem ->
                val product = ProductsRepository().getNested(cartItem.productId!!)
                cartItems.add(
                    CartItemModel(cartItem.productId, product, cartItem.quantity, id=cartItem.id)
                )
            }
            cart.cartItems = cartItems
        }
        return cart
    }


    suspend fun getNested(userId: String): CartModel? {
        val cart = get(userId)
        return getNestedDetails(cart)
    }

    companion object {
        private val TAG = CartRepository::class.java.name
        private val dbReference =
            FirebaseDatabase.getInstance().getReference(FirebaseDBEndPoints.CARTS)
    }
}