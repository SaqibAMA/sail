package com.functrco.sail.adaptors


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemProductBinding
import com.functrco.sail.models.ProductModel
import com.functrco.sail.utils.Util

class ProductAdaptor(): RecyclerView.Adapter<ProductAdaptor.ViewHolder>() {
    private var products: List<ProductModel> = listOf()
    var onItemClick: ((ProductModel) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductModel){
            Glide
                .with(this.itemView.context)
                .load(product.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.default_product_img)
                .into(binding.productImage)

            binding.productName.text = product.name
            binding.productPrice.text = Util.toCurrency(product.price)
            binding.productRating.text = String.format("%.1f", Util.calculateRating(product.reviews))
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