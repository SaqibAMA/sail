package com.functrco.sail.screens.main.cart


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemCartBinding

class CartAdaptor(): RecyclerView.Adapter<CartAdaptor.ViewHolder>() {
    private var carts: List<CartModel> = listOf()
    var onItemClick: ((CartModel) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cart: CartModel){
            binding.productImage.setImageResource(
                cart.product.imageId ?: R.drawable.default_product_img
            )

            binding.productTitleTextView.text = cart.product.name
            binding.productDescriptionTextView.text = cart.product.description.orEmpty().ifEmpty { binding.cartCard.context.getString(R.string.default_product_description) }
            binding.productPriceTextView.text = "$${String.format("%.2f", cart.product.price)}"
            binding.productQuantityTextView.text = cart.quantity.toString()
        }
        init {
            binding.productImage.setOnClickListener{
                onItemClick?.invoke(carts[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    fun setListData(data: List<CartModel>){
        this.carts = data.toMutableList()
    }
}