package com.functrco.sail.screens.main.home.product


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.FragmentHomeBinding
import com.functrco.sail.databinding.ItemProductBinding

class ProductAdaptor(): RecyclerView.Adapter<ProductAdaptor.ViewHolder>() {
    private lateinit var products: List<Product>

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.productImage.setImageResource(
                product.imageId ?: R.drawable.img_headphone_product
            )
            binding.productName.text = product.name
            binding.productPrice.text = product.price.toString()
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