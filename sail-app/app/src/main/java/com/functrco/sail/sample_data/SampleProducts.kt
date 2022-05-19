package com.functrco.sail.sample_data

import com.functrco.sail.R
import com.functrco.sail.models.ProductModel

object SampleProducts {
    fun getAll(): List<ProductModel> {

        val reviews = SampleReviews.getAll()

        return mutableListOf(
            ProductModel(
                "Beautiful Shoes",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                15.99F,
                R.drawable.default_product_img,
                null,
                "Nishat",
                "-N28U0LdZpTFGq2RxdYD",
                10,
                null,
                null,
                reviews.take(2)
            ),
            ProductModel(
                "Amazing Phone",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                10.99F,
                R.drawable.img_phone_product,
                null,
                "Nvidia",
                "-N28U0qekH7FWYg3kt1d",
                125,
                null,
                null,
                reviews.takeLast(2)
            ),
            ProductModel(
                "Amazing Headphone",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum",
                99F,
                R.drawable.img_headphone_product,
                null,
                "Samsung",
                "-N28U0qekH7FWYg3kt1d",
                95,
                null,
                null,
                listOf(reviews[1])
            ),
        )
    }

}