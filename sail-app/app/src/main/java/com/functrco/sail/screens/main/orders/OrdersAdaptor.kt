package com.functrco.sail.screens.main.orders


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemOrderBinding

class OrdersAdaptor(): RecyclerView.Adapter<OrdersAdaptor.ViewHolder>() {
    private var orders: List<Order> = listOf()
    var onItemClick: ((Order) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order){
            binding.productImage.setImageResource(
                order.product.imageId ?: R.drawable.default_product_img
            )
            binding.productTitleTextView.text = order.product.name
            binding.productPriceChip.text = order.product.price.toString()
            binding.productStatusChip.text = order.status
        }
        init {
            binding.orderCard.setOnClickListener{
                onItemClick?.invoke(orders[adapterPosition])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun setListData(data: List<Order>){
        this.orders = data.toMutableList()
    }
}