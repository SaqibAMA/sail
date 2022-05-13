package com.functrco.sail.screens.main.home

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.functrco.sail.databinding.FragmentHomeBinding
import com.functrco.sail.screens.main.home.category.CategoryAdaptor
import com.functrco.sail.screens.main.home.category.CategoryViewModel
import com.functrco.sail.screens.main.home.product.GridSpacingItemDecoration
import com.functrco.sail.screens.main.home.product.ProductAdaptor
import com.functrco.sail.screens.main.home.product.ProductViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productViewModel: ProductViewModel

    private val categoriesAdaptor = CategoryAdaptor()
    private val productsAdaptor = ProductAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]


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
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.app_icon_size)
        binding.productsRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, true, 0, false))
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