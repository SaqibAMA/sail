package com.functrco.sail.screens.main.products


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemProductBinding

class ProductAdaptor(): RecyclerView.Adapter<ProductAdaptor.ViewHolder>() {
    private var products: List<ProductModel> = listOf()
    var onItemClick: ((ProductModel) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductModel){
            binding.productImage.setImageResource(
                product.imageId ?: R.drawable.default_product_img
            )
            binding.productName.text = product.name
            binding.productPrice.text = "$${String.format("%.2f", product.price)}"
        }

        init {
            binding.productCard.setOnClickListener{
                onItemClick?.invoke(products[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setListData(data: List<ProductModel>){
        this.products = data.toMutableList()
    }
}