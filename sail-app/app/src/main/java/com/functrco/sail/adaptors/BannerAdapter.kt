package com.functrco.sail.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.R
import com.functrco.sail.databinding.ItemBannerBinding
import com.functrco.sail.models.BannerModel
import com.functrco.sail.utils.Util

class BannerAdapter: RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    private var banners: List<BannerModel> = listOf()

    inner class ViewHolder(private val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(banner: BannerModel){
            Util.loadImage(banner.imageUrl!!, binding.saleBannerImgView, R.drawable.img_sale_banner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(banners[position])
    }

    override fun getItemCount(): Int {
        return banners.size
    }

    fun setListData(data: List<BannerModel>){
        this.banners = data.toMutableList()
    }
}