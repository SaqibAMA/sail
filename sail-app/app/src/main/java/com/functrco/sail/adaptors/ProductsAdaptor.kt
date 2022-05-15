package com.functrco.sail.screens.main.home.products_parent


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.functrco.sail.ProductPage
import com.functrco.sail.databinding.ItemProductsBinding
import com.functrco.sail.models.ProductsModel
import com.functrco.sail.adaptors.ProductAdaptor

class ProductsParentAdaptor(): RecyclerView.Adapter<ProductsParentAdaptor.ViewHolder>() {
    private var productsParents: List<ProductsModel> = listOf()

    inner class ViewHolder(private val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(productsParent: ProductsModel){
            binding.productsTitle.text = productsParent.title
            setProducts(binding, productsParent)
        }
    }

    // bind categories to the recycler view
    private fun setProducts(binding: ItemProductsBinding, productsParent: ProductsModel){
        val productAdaptor = ProductAdaptor()
        productAdaptor.setListData(productsParent.productsParent!!)
        binding.productsRecyclerView.adapter = productAdaptor
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(binding.productsRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        productAdaptor.onItemClick = {
            val redirectToProductPage = Intent(binding.productsRecyclerView.context, ProductPage::class.java)
            // TODO: pass product information through intent
            binding.productsRecyclerView.context.startActivity(redirectToProductPage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productsParents[position])
    }

    override fun getItemCount(): Int {
        return productsParents.size
    }

    fun setListData(data: List<ProductsModel>){
        this.productsParents = data.toMutableList()
    }
}