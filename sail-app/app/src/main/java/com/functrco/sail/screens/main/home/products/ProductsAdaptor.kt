package com.functrco.sail.screens.main.home.products


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemProductBinding

class ProductsAdaptor(): RecyclerView.Adapter<ProductsAdaptor.ViewHolder>() {
    private lateinit var products: List<Product>
    var onItemClick: ((Product) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.productImage.setImageResource(
                product.imageId ?: R.drawable.default_product_img
            )
            binding.productName.text = product.name
            binding.productPrice.text = product.price.toString()
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

    fun setListData(data: List<Product>){
        this.products = data.toMutableList()
    }
}