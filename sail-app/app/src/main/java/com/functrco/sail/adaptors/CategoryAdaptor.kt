package com.functrco.sail.adaptors


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemCategoryBinding
import com.functrco.sail.models.CategoryModel
import com.functrco.sail.models.ProductModel

class CategoryAdaptor() : RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {
    private var categories: List<CategoryModel> = listOf()
    var onItemClick: ((CategoryModel) -> Unit)? = null


    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryModel) {
            Glide
                .with(this.itemView.context)
                .load(category.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.default_product_img)
                .into(binding.categoryImage)
            binding.categoryName.text = category.name
        }

        init {
            binding.categoryImage.setOnClickListener{
                onItemClick?.invoke(categories[adapterPosition])
            }
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

    fun setListData(data: List<CategoryModel>) {
        this.categories = data.toMutableList()
    }
}