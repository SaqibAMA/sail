package com.functrco.sail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.models.ProductModel
import com.functrco.sail.models.ReviewModel
import com.functrco.sail.viewModels.ProductsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReviewsPage : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: RecyclerView.Adapter<ProductPageReviewAdapter.ViewHolder>
    private lateinit var reviewRecyclerView: RecyclerView
    private var reviews: List<ReviewModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews_page)


        val reviewsType = object : TypeToken<List<ReviewModel>>() {}.type
        // get product information from the intent
        reviews = Gson().fromJson<List<ReviewModel>>(
            intent.getStringExtra("reviews_info"),
            reviewsType
        )
        Log.d(TAG, reviews.toString())


        // bind back button
        findViewById<ImageButton>(R.id.top_nav_back_btn).setOnClickListener {
            finish()
        }

        // TODO: Pull reviews for the product from Firebase
        fillReviews()
    }

    fun fillReviews() {
        if(reviews != null){
            layoutManager = LinearLayoutManager(this)
            reviewRecyclerView = findViewById(R.id.reviews_page_list)
            reviewRecyclerView.layoutManager = layoutManager
            adapter = ProductPageReviewAdapter(reviews!!)
            reviewRecyclerView.adapter = adapter
        }
    }

    companion object {
        private val TAG = ReviewsPage::class.java.name
    }
}