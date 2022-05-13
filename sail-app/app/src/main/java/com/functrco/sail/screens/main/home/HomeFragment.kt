package com.functrco.sail.screens.main.home

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.functrco.sail.ProductPage
import com.functrco.sail.R
import com.functrco.sail.databinding.FragmentHomeBinding
import com.functrco.sail.screens.main.home.categories.CategoriesAdaptor
import com.functrco.sail.screens.main.home.categories.CategoriesViewModel
import com.functrco.sail.screens.main.home.products.GridSpaceItemDecoration
import com.functrco.sail.screens.main.home.products.ProductsAdaptor
import com.functrco.sail.screens.main.home.products.ProductsViewModel
import com.functrco.sail.utils.DisplayUtil


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoriesViewModel
    private lateinit var productViewModel: ProductsViewModel

    private val categoriesAdaptor = CategoriesAdaptor()
    private val productsAdaptor = ProductsAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        categoryViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        productViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]


        setCategories()
        observeCategories()

        setProducts()
        observeProducts()

        return binding.root
    }


    // bind categories to the recycler view
    private fun setCategories(){
        binding.categoriesRecyclerView.adapter = categoriesAdaptor
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    // set observer on the categories list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeCategories() {
        categoryViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                categoriesAdaptor.setListData(it)
                categoriesAdaptor.notifyDataSetChanged()
            } else {
                Log.d(TAG, "observeCategories(): null")
            }
        })
        categoryViewModel.fetchCategories()
    }



    // bind categories to the recycler view
    private fun setProducts(){
        binding.productsRecyclerView.adapter = productsAdaptor
        binding.productsRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.productsRecyclerView.addItemDecoration(GridSpaceItemDecoration(2, 0F, DisplayUtil.dp2dx(10F, resources)))

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
            if (it != null) {
                productsAdaptor.setListData(it)
                productsAdaptor.notifyDataSetChanged()
            } else {
                Log.d(TAG, "observeProducts(): null")
            }
        })
        productViewModel.fetchProducts()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}