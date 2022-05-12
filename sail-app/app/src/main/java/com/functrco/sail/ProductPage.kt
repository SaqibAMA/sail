package com.functrco.sail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ProductPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        val backBtn = findViewById<ImageButton>(R.id.product_page_back_btn)
        backBtn.setOnClickListener {
            finish()
        }

    }
}