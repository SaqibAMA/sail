package com.functrco.sail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.functrco.sail.firebase.repository.CartRepository
import com.functrco.sail.firebase.repository.OrdersRepository
import com.functrco.sail.firebase.repository.ProductsRepository
import com.functrco.sail.models.*
import com.functrco.sail.screens.main.CartFragment
import com.functrco.sail.utils.Util
import com.functrco.sail.viewModels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.w3c.dom.Text
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.fixedRateTimer

class ProductPage : AppCompatActivity() {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    var adapter = ProductPageReviewAdapter(listOf())
    private lateinit var reviewList: RecyclerView
    private lateinit var product: ProductModel
    private var reviews: List<ReviewModel>? = null
    private var user: FirebaseUser? = null
    private var ratingStars: Float = 1F

    companion object {
        private const val TAG = "PRODUCT_PAGE"  // for debug
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        user = FirebaseAuth.getInstance().currentUser
        // get product information from the intent
        product = Gson().fromJson<ProductModel>(
            intent.getStringExtra("product_info"),
            ProductModel::class.java
        )
        reviews = product.reviews?.sortedByDescending {
            it.date
        }
        Log.d(TAG, product.toString())


        // setup user review
        setupUserReview()


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

        fillReviews()
    }

    private fun setupUserReview() {
        if (user != null) {

            findViewById<RatingBar>(R.id.user_rating_stars).setOnRatingBarChangeListener { ratingBar, rating, b ->
                ratingStars = ratingBar.rating
            }

            val orderId: String? = intent.getStringExtra("order_id")
            intent.extras?.remove("order_id")

            var order: OrderModel? = null
            val ratingLayout = findViewById<LinearLayout>(R.id.user_rating_layout)

            if (orderId != null) {
                GlobalScope.launch {
                    order = OrdersRepository().get(orderId)
                    Log.d(TAG, order.toString())
                    withContext(Dispatchers.Main) {
                        ratingLayout.visibility =
                            if (order?.reviewed == false && order?.status.equals(
                                    "completed",
                                    ignoreCase = true
                                )
                            ) View.VISIBLE else View.GONE
                    }
                }

                findViewById<Button>(R.id.user_rating_submit_btn).setOnClickListener {
                    val review = ReviewModel(
                        user?.uid!!,
                        null,
                        findViewById<TextView>(R.id.user_rating_text).text.toString(),
                        ratingStars,
                        Date().toString()
                    )

                    // add a review and get review id
                    GlobalScope.launch {
                        val reviewId = ProductsRepository().addReview(product.id!!, review)
                        if (reviewId != null) {
                            // update order status
                            OrdersRepository().updateIsOrderReviewed(orderId, true)


                            if (user != null) {
                                review.user = UserModel(
                                    user?.displayName,
                                    user?.phoneNumber,
                                    user?.email,
                                    user?.photoUrl.toString(),
                                    "",
                                    user?.uid
                                )
                            }


                            // update reviews adaptor
                            review.id = reviewId
                            val temp = reviews?.toMutableList()
                            temp?.add(review)
                            reviews = temp

                            adapter.data = reviews ?: listOf()

                            withContext(Dispatchers.Main) {
                                adapter.notifyDataSetChanged()
                                setViewsData()
                                ratingLayout.visibility = View.GONE
                            }
                        }
                    }
                }
            } else {
                findViewById<LinearLayout>(R.id.product_page_bottom_bar).visibility = View.VISIBLE
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun handleAddToCart() {
        Log.d(TAG, "Add to cart")
        val redirectToCartPage = Intent(this, MainActivity::class.java)
        // insert product into cart
        GlobalScope.launch {
            if (user != null) {
                val cart = CartModel(listOf(CartItemModel(product.id, product)), user?.uid!!)
                CartRepository().insert(user?.uid!!, cart)
                redirectToCartPage.putExtra("go_to_cart_page", true)
                startActivity(redirectToCartPage)
            }
        }
    }

    private fun handleBuyNow() {
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
        val rating = Util.calculateRating(product.reviews)

        Glide
            .with(this)
            .load(product.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.default_product_img)
            .into(findViewById(R.id.product_img))
        findViewById<TextView>(R.id.product_page_product_title).text = product.name
        findViewById<TextView>(R.id.product_page_price).text = Util.toCurrency(product.price)
        findViewById<TextView>(R.id.product_description).text = product.description
        findViewById<TextView>(R.id.product_rating).text = String.format("%.1f", rating)
        findViewById<TextView>(R.id.product_page_orders_count).text = product.ordersCount.toString()
        findViewById<RatingBar>(R.id.productRatingStars).rating = rating / 5F
    }
}