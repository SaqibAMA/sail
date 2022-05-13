package com.functrco.sail

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class PageTopNav @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private var attributes: TypedArray
    private lateinit var title: TextView

    init {

        // This helps in appending the contents into the view
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.page_top_nav, this, true)

        // This gets all the attributes that have been passed by the user in the component
        attributes = context.obtainStyledAttributes(attrs, R.styleable.PageTopNav)

        // registers components i.e. findViewByID
        registerComponents()

        // gets the values from the attributes and sets the values of the fields
        setValues()

    }

    fun registerComponents() {
        // registering all components to private variables.
        title = findViewById(R.id.page_top_nav_title)
    }

    fun setValues() {
        // takes value from args and sets the values
        title.text = attributes.getString(R.styleable.PageTopNav_title)
    }

}