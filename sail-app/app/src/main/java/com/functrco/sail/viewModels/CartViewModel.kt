package com.functrco.sail.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.functrco.sail.firebase.repository.CartRepository
import com.functrco.sail.models.CartItemModel
import com.functrco.sail.models.CartModel
import com.functrco.sail.models.ProductModel
import com.functrco.sail.sample_data.SampleCarts
import com.functrco.sail.utils.Util
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private var _cart = CartModel()
    private var cart = MutableLiveData<CartModel?>()

    fun getObserver() = cart

    /* TODO: replace the code to fetch carts from API */
    fun getAll(userId: String): MutableLiveData<CartModel?>{
        viewModelScope.launch {
            _cart = CartRepository().getNested(userId) ?: CartModel()
            cart.postValue(_cart)
        }
        return cart
    }

    fun add(cartItem: CartItemModel) {
        var cartItems = _cart.cartItems?.toMutableList()
        if(cartItems == null){
            cartItems = mutableListOf(cartItem)
        }
        else{
            if(Util.isItemExists(_cart, cartItem) == false){
                cartItems.add(cartItem)
            }
        }
        _cart.cartItems = cartItems
        cart.postValue(_cart)
    }


    suspend fun insert(userId: String, cart: CartModel?) : String? {
        return if(cart != null) CartRepository().insert(userId, cart) else null
    }

    suspend fun insert(userId: String, cartItem: CartItemModel?): String? {
        if(cartItem != null){
            val cart = CartModel(listOf(cartItem), userId)
            return insert(userId, cart)
        }
        return null
    }
}




