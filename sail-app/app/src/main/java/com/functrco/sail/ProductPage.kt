package com.functrco.sail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductPage : AppCompatActivity() {

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: RecyclerView.Adapter<ProductPageReviewAdapter.ViewHolder>
    lateinit var reviewList: RecyclerView


    companion object {
        private const val TAG = "PRODUCT_PAGE"  // for debug
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        // bind back button
        findViewById<ImageButton>(R.id.product_page_back_btn).apply {
            setOnClickListener {
                finish()
            }
        }

        // bind add to cart button
        findViewById<Button>(R.id.product_page_add_to_cart).apply {
            setOnClickListener {
                handleAddToCart()
            }
        }

        // bind buy now button
        findViewById<Button>(R.id.product_page_buy_now).apply {
            setOnClickListener {
                handleBuyNow()
            }
        }

        // TODO: Pull reviews for the product from Firebase

        // filling dummy data into product page
        fillReviews(
            listOf(
                ReviewModel(
                    getString(R.string.default_name),
                    getString(R.string.default_review_rating),
                    getString(R.string.default_review),
                    getString(R.string.default_review_date_posted)
                ),
                ReviewModel(
                    getString(R.string.default_name),
                    getString(R.string.default_review_rating),
                    getString(R.string.default_review),
                    getString(R.string.default_review_date_posted)
                ),
                ReviewModel(
                    getString(R.string.default_name),
                    getString(R.string.default_review_rating),
                    getString(R.string.default_review),
                    getString(R.string.default_review_date_posted)
                )
            )
        )

    }

    fun handleAddToCart() {
        Log.d(TAG, "Add to cart")
    }

    fun handleBuyNow() {
        Log.d(TAG, "Buy now")
    }

    fun fillReviews(data: List<ReviewModel>) {
        layoutManager = LinearLayoutManager(this)
        reviewList = findViewById(R.id.product_page_review_list)
        reviewList.layoutManager = layoutManager
        adapter = ProductPageReviewAdapter(data)
        reviewList.adapter = adapter
    }

}