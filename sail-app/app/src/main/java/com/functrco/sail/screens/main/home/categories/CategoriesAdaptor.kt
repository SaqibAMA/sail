package com.functrco.sail.screens.main.home.categories


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.ProductPage
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemCategoryBinding
import com.functrco.sail.screens.main.home.HomeFragment

class CategoriesAdaptor() : RecyclerView.Adapter<CategoriesAdaptor.ViewHolder>() {
    private lateinit var categories: List<Category>

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryImage.setImageResource(
                category.imageId ?: R.drawable.default_product_img
            )
            binding.categoryName.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }


    override fun getItemCount(): Int {
        return categories.size
    }

    fun setListData(data: List<Category>) {
        this.categories = data.toMutableList()
    }
}