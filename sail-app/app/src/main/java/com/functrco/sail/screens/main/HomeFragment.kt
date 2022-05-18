package com.functrco.sail.screens.main

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.functrco.sail.MainActivity
import com.functrco.sail.ProductPage
import com.functrco.sail.R
import com.functrco.sail.databinding.FragmentHomeBinding
import com.functrco.sail.adaptors.CategoryAdaptor
import com.functrco.sail.viewModels.CategoryViewModel
import com.functrco.sail.adaptors.ProductsParentAdaptor
import com.functrco.sail.models.CategoryModel
import com.functrco.sail.models.ProductModel
import com.functrco.sail.utils.Util
import com.functrco.sail.viewModels.ProductsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlin.concurrent.fixedRateTimer


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productsParentViewModel: ProductsViewModel

    private lateinit var categoriesAdaptor: CategoryAdaptor
    private lateinit var productsParentsAdaptor: ProductsParentAdaptor
    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        productsParentViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        user = FirebaseAuth.getInstance().currentUser

        setUserInfo()

        setCategories()
        observeCategories()

        setProductsParents()
        observeProductsParents()

        return binding.root
    }

    private fun setUserInfo() {
        if(user != null){
            Glide
                .with(this)
                .load(user?.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.default_product_img)
                .into(binding.userDisplayImage)
            binding.userDisplayName.text = user?.displayName
        }
    }


    // bind categories to the recycler view
    private fun setCategories() {
        categoriesAdaptor = CategoryAdaptor()
        binding.categoriesRecyclerView.adapter = categoriesAdaptor
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        categoriesAdaptor.onItemClick = {
            val bundle = Bundle()
            bundle.putString("category_name", it.name)
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundle)
        }
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
        categoryViewModel.getAll()
    }


    // bind categories to the recycler view
    private fun setProductsParents() {
        productsParentsAdaptor = ProductsParentAdaptor()
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
        productsParentViewModel.getAll()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}