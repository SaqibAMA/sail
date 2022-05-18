package com.functrco.sail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.functrco.sail.models.ReviewModel

class ProductPageReviewAdapter(data: List<ReviewModel>) :
    RecyclerView.Adapter<ProductPageReviewAdapter.ViewHolder>() {

    var data: List<ReviewModel> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_page_review, parent, false)
        return ViewHolder(listItemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.r_name.text = data[position].user?.name
        holder.r_rating.text = String.format("%.1f", data[position].rating)
        holder.r_text.text = data[position].text
        holder.r_date.text = data[position].date
        Glide
            .with(holder.itemView.context)
            .load(data[position].user?.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_avatar_fg)
            .into(holder.r_user_img)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var r_name: TextView = itemView.findViewById(R.id.product_page_review_user_name)
        var r_rating: TextView = itemView.findViewById(R.id.product_page_review_rating)
        var r_text: TextView = itemView.findViewById(R.id.product_page_review_text)
        var r_date: TextView = itemView.findViewById(R.id.product_page_review_date_posted)
        var r_user_img: ImageView = itemView.findViewById(R.id.product_page_review_user_avatar)
    }
}