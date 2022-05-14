package com.functrco.sail.screens.main.cart


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemCartProductBinding

class CartAdaptor(): RecyclerView.Adapter<CartAdaptor.ViewHolder>() {
    private var carts: List<CartItemModel> = listOf()
    var onItemClick: ((CartItemModel) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemCartProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cartItem: CartItemModel){
            binding.productImage.setImageResource(
                cartItem.product.imageId ?: R.drawable.default_product_img
            )

            binding.productTitleTextView.text = cartItem.product.name
            binding.productDescriptionTextView.text = cartItem.product.description.orEmpty().ifEmpty { binding.cartCard.context.getString(R.string.default_product_description) }
            binding.productPriceTextView.text = "$${String.format("%.2f", cartItem.product.price)}"
            binding.productQuantityTextView.text = cartItem.quantity.toString()
        }

        fun setPlusClickListener(position: Int){
            binding.plusTextView.setOnClickListener {
                carts[position].quantity++
                notifyItemChanged(position)
            }
        }

        fun setMinusClickListener(position: Int){
            binding.minusTextView.setOnClickListener {
                if(carts[position].quantity > 0){
                    carts[position].quantity--
                    notifyItemChanged(position)
                }
                notifyItemChanged(position)
            }
        }

        init {
            binding.productImage.setOnClickListener{
                onItemClick?.invoke(carts[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
        holder.setPlusClickListener(position)
        holder.setMinusClickListener(position)
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    fun setListData(data: List<CartItemModel>){
        this.carts = data.toMutableList()
    }
}