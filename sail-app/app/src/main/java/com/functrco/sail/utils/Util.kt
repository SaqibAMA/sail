package com.functrco.sail.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.functrco.sail.R
import com.functrco.sail.models.CartItemModel
import com.functrco.sail.models.CartModel
import com.functrco.sail.models.ReviewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat

object Util {

    // convert dp to dx
    fun dp2dx(dp: Float, resources: Resources): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
    }

    fun <T> toSerializable(t: T): String {
        return Gson().toJson(t)
    }

    fun isItemExists(myCart: CartModel, cartItem: CartItemModel): Boolean? {
        return myCart.cartItems?.any { c ->
            (c.id != null && c.id == cartItem.id) ||
                    (c.productId != null && c.productId == cartItem.productId) ||
                    (c.product?.id != null && c.product?.id == cartItem.product?.id)
        }
    }

    // calculate total payment of a cart
    fun calculateTotalPayment(cartItems: MutableList<CartItemModel>): Float {
        var totalPayment = 0F
        cartItems.forEach { cartItem ->
            cartItem.product?.let { product ->
                totalPayment += product.price!! * cartItem.quantity
            }
        }
        return totalPayment
    }

    fun toCurrency(price: Float?, precision: Int = 2, currencySymbol: Char = '$'): String {
        return "$currencySymbol${String.format("%.${precision}f", price ?: 0F)}"
    }

    fun calculateRating(reviews: List<ReviewModel>?): Float {
        var rating = 0F
        if (reviews != null) {
            reviews.forEach { review ->
                if (review.rating != null) {
                    rating += review.rating!!
                }
            }
            rating /= reviews.size
        }
        return rating
    }

    fun loadImage(imageUrl: String, imageView: ImageView, defaultResourceId: Int = R.drawable.default_product_img) {
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(defaultResourceId)
            .into(imageView)
    }


    fun showToast(context: Context?, msg: Any?) {
        if (msg != null) {
            Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}