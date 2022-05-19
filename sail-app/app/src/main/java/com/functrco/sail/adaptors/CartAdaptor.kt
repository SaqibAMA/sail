package com.functrco.sail.adaptors


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemCartProductBinding
import com.functrco.sail.models.CartItemModel
import com.functrco.sail.utils.Util

class CartAdaptor(): RecyclerView.Adapter<CartAdaptor.ViewHolder>() {
    var carts: MutableList<CartItemModel> = mutableListOf()
    var onPlusClick: ((CartItemModel) -> Unit)? = null
    var onMinusClick: ((CartItemModel) -> Unit)? = null
    var onDeleteItemClick: ((CartItemModel, Int) -> Unit)? = null


    inner class ViewHolder(private val binding: ItemCartProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cartItem: CartItemModel){
            Glide
                .with(this.itemView.context)
                .load(cartItem.product!!.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.default_product_img)
                .into(binding.productImage)
            binding.productTitleTextView.text = cartItem.product!!.name
            binding.productDescriptionTextView.text = cartItem.product!!.description.orEmpty().ifEmpty { binding.cartCard.context.getString(R.string.default_product_description) }
            binding.productPriceTextView.text = Util.toCurrency(cartItem.product?.price)
            binding.productQuantityTextView.text = cartItem.quantity.toString()
        }

        init {
            binding.plusTextView.setOnClickListener{
                onPlusClick?.invoke(carts[adapterPosition])
            }

            binding.minusTextView.setOnClickListener{
                onMinusClick?.invoke(carts[adapterPosition])
            }

            binding.productDeleteButton.setOnClickListener {
                onDeleteItemClick?.invoke(carts[adapterPosition], adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    fun setListData(data: List<CartItemModel>){
        this.carts = data.toMutableList()
    }
}