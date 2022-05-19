package com.functrco.sail.sample_data

import com.functrco.sail.R
import com.functrco.sail.models.ReviewModel

object SampleReviews {
    fun getAll(): List<ReviewModel>{
            return listOf(
                ReviewModel(
                    "iOvO71u3w1R7VIUGdK0wEPrTxxY2",
                    null,
                    "I found this product really useful. It is an absolutely killer deal and I couldn\\'t ask for more.",
                    4.4F,
                    "20-12-2002"
                ),
                ReviewModel(
                    "iOvO71u3w1R7VIUGdK0wEPrTxxY2",
                    null,
                    "I found1 this product really useful. It is an absolutely killer deal and I couldn\\'t ask for more.",
                    5F,
                    "12-12-2002"
                ),
                ReviewModel(
                    "iOvO71u3w1R7VIUGdK0wEPrTxxY2",
                    null,
                    "I found2 this product really useful. It is an absolutely killer deal and I couldn\\'t ask for more.",
                    2.4F,
                    "12-05-2002"
                )
            )
    }
}