package com.functrco.sail.screens.main.home

import android.annotation.SuppressLint
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.functrco.sail.ProductPage
import com.functrco.sail.databinding.FragmentHomeBinding
import com.functrco.sail.screens.main.home.categories.CategoryAdaptor
import com.functrco.sail.screens.main.home.categories.CategoryViewModel
import com.functrco.sail.screens.main.home.products_parent.ProductsParentAdaptor
import com.functrco.sail.screens.main.home.products_parent.ProductsViewModel
import com.functrco.sail.screens.main.products.ProductAdaptor
import com.functrco.sail.screens.main.products.ProductViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productsParentViewModel: ProductsViewModel

    private val categoriesAdaptor = CategoryAdaptor()
    private val productsParentsAdaptor = ProductsParentAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        productsParentViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]


        setCategories()
        observeCategories()

        setProductsParents()
        observeProductsParents()

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
    private fun setProductsParents(){
        binding.productsParentRecyclerView.adapter = productsParentsAdaptor
        binding.productsParentRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    // set observer on the categories list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeProductsParents() {
        productsParentViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                productsParentsAdaptor.setListData(it)
                productsParentsAdaptor.notifyDataSetChanged()
            } else {
                Log.d(TAG, "observeProductsParents(): null")
            }
        })
        productsParentViewModel.fetchProductsParents()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}