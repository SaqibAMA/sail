package com.functrco.sail

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class ProductPageReview @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var attributes: TypedArray

    private lateinit var r_name: TextView
    private lateinit var r_rating: TextView
    private lateinit var r_text: TextView
    private lateinit var r_date: TextView

    init {

        // This helps in appending the contents into the view
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.product_page_review, this, true)

        // This gets all the attributes that have been passed by the user in the component
        attributes = context.obtainStyledAttributes(attrs, R.styleable.ProductPageReview)

        // registers components i.e. findViewByID
        registerComponents()

        // gets the values from the attributes and sets the values of the fields
        setValues()

    }

    fun registerComponents() {
        // registering all components to private variables.
        r_name = findViewById(R.id.product_page_review_user_name)
        r_text = findViewById(R.id.product_page_review_text)
        r_date = findViewById(R.id.product_page_review_date_posted)
        r_rating = findViewById(R.id.product_page_review_rating)
    }

    fun setValues() {
        // takes value from args and sets the values
        r_name.text = attributes.getString(R.styleable.ProductPageReview_review_name)
        r_text.text = attributes.getString(R.styleable.ProductPageReview_review_text)
        r_date.text = attributes.getString(R.styleable.ProductPageReview_review_date)
        r_rating.text = "${attributes.getString(R.styleable.ProductPageReview_review_rating)}/5"
    }

}