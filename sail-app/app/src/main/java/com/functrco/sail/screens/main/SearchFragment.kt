package com.functrco.sail.screens.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.functrco.sail.ProductPage
import com.functrco.sail.databinding.FragmentSearchBinding
import com.functrco.sail.utils.GridSpaceItemDecoration
import com.functrco.sail.adaptors.ProductAdaptor
import com.functrco.sail.viewModels.ProductViewModel
import com.functrco.sail.utils.DisplayUtil

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private val productsAdaptor = ProductAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        setProducts()
        observeProducts()

        return binding.root
    }

    // bind categories to the recycler view
    private fun setProducts() {
        binding.productsRecyclerView.adapter = productsAdaptor
        binding.productsRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.productsRecyclerView.addItemDecoration(
            GridSpaceItemDecoration(
                2,
                0F,
                DisplayUtil.dp2dx(10F, resources)
            )
        )

        productsAdaptor.onItemClick = {
            val redirectToProductPage = Intent(activity, ProductPage::class.java)
            // TODO: pass product information through intent
            startActivity(redirectToProductPage)
        }
    }

    // set observer on the categories list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeProducts() {
        productViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            productsAdaptor.setListData(it)
            productsAdaptor.notifyDataSetChanged()
        })
        productViewModel.fetchProducts()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "ProductFragment"
    }
}