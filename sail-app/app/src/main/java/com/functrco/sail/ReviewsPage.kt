package com.functrco.sail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReviewsPage : AppCompatActivity() {

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: RecyclerView.Adapter<ProductPageReviewAdapter.ViewHolder>
    lateinit var reviewList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews_page)

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

    fun fillReviews(data: List<ReviewModel>) {
        layoutManager = LinearLayoutManager(this)
        reviewList = findViewById(R.id.reviews_page_list)
        reviewList.layoutManager = layoutManager
        adapter = ProductPageReviewAdapter(data)
        reviewList.adapter = adapter
    }

}