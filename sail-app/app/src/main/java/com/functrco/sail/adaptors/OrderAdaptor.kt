package com.functrco.sail.adaptors


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemOrderBinding
import com.functrco.sail.models.OrderModel

class OrderAdaptor(): RecyclerView.Adapter<OrderAdaptor.ViewHolder>() {
    private var orders: List<OrderModel> = listOf()
    var onItemClick: ((OrderModel) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(order: OrderModel){
            binding.productImage.setImageResource(
                order.product!!.imageResourceId ?: R.drawable.default_product_img
            )
            binding.productTitleTextView.text = order.product!!.name
            binding.productDescriptionTextView.text = order.product!!.description.orEmpty().ifEmpty { binding.orderCard.context.getString(R.string.default_product_description) }
            binding.productPriceChip.text = "$${String.format("%2.2f", order.product!!.price)}"
            binding.productStatusChip.text = order.status
        }
        init {
            binding.productImage.setOnClickListener{
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

    fun setListData(data: List<OrderModel>){
        this.orders = data.toMutableList()
    }
}