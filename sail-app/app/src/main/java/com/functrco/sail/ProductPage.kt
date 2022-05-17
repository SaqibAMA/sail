package com.functrco.sail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ReviewModel
import com.functrco.sail.screens.main.CartFragment
import com.functrco.sail.utils.Util
import com.google.gson.Gson
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.fixedRateTimer

class ProductPage : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: RecyclerView.Adapter<ProductPageReviewAdapter.ViewHolder>
    private lateinit var reviewList: RecyclerView
    private lateinit var product: ProductModel
    private var reviews: List<ReviewModel>? = null

    companion object {
        private const val TAG = "PRODUCT_PAGE"  // for debug
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        // get product information from the intent
        product = Gson().fromJson<ProductModel>(
            intent.getStringExtra("product_info"),
            ProductModel::class.java
        )
        reviews = product.reviews?.sortedByDescending {
            it.date
        }
        Log.d(TAG, product.toString())

        // TODO: bind remaining data i.e orders and reviews
        setViewsData()

        // bind back button
        findViewById<ImageButton>(R.id.top_nav_back_btn).setOnClickListener {
            finish()
        }

        // bind add to cart button
        findViewById<Button>(R.id.product_page_add_to_cart).setOnClickListener {
            handleAddToCart()
        }

        // bind buy now button
        findViewById<Button>(R.id.product_page_buy_now).setOnClickListener {
            handleBuyNow()
        }

        findViewById<TextView>(R.id.product_page_view_more).setOnClickListener {
            val i = Intent(this, ReviewsPage::class.java)
            i.putExtra("reviews_info", Util.toSerializable(reviews))
            startActivity(i)
        }

        // filling dummy data into product page
        fillReviews()
    }

    fun handleAddToCart() {
        Log.d(TAG, "Add to cart")
        val redirectToCartPage = Intent(this, MainActivity::class.java)
        redirectToCartPage.putExtra("product_info", Util.toSerializable(product))
        startActivity(redirectToCartPage)
    }

    fun handleBuyNow() {
        Log.d(TAG, "Buy now")
    }

    private fun fillReviews() {
        val data = reviews?.take(5)
        if (data != null) {
            layoutManager = LinearLayoutManager(this)
            reviewList = findViewById(R.id.product_page_review_list)
            reviewList.layoutManager = layoutManager
            adapter = ProductPageReviewAdapter(data)
            reviewList.adapter = adapter
        }
    }


    private fun setViewsData() {
        Glide
            .with(this)
            .load(product.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.default_product_img)
            .into(findViewById(R.id.product_img))
        findViewById<TextView>(R.id.product_page_product_title).text = product.name
        findViewById<TextView>(R.id.product_page_price).text = Util.toCurrency(product.price)
        findViewById<TextView>(R.id.product_description).text = product.description
        findViewById<TextView>(R.id.product_rating).text =
            String.format("%.1f", Util.calculateRating(product.reviews))

    }
}