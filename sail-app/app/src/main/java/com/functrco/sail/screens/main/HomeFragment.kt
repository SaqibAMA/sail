package com.functrco.sail.screens.main

import android.annotation.SuppressLint

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
import com.functrco.sail.R
import com.functrco.sail.adaptors.BannerAdapter
import com.functrco.sail.databinding.FragmentHomeBinding
import com.functrco.sail.adaptors.CategoryAdaptor
import com.functrco.sail.viewModels.CategoryViewModel
import com.functrco.sail.adaptors.ProductsParentAdaptor
import com.functrco.sail.api.ApiClient
import com.functrco.sail.api.services.BannerService
import com.functrco.sail.viewModels.ProductsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*
import retrofit2.HttpException


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productsParentViewModel: ProductsViewModel

    private lateinit var categoriesAdaptor: CategoryAdaptor
    private lateinit var productsParentsAdaptor: ProductsParentAdaptor
    private lateinit var bannersAdaptor: BannerAdapter
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

        setBanners()
        fillBanners()

        setUserInfo()

        setCategories()
        observeCategories()

        setProductsParents()
        observeProductsParents()

        return binding.root
    }

    private fun fillBanners() {
        val service = ApiClient.buildService(BannerService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getBanners()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val banners = response.body()
                        Log.d(TAG, banners.toString())
                        bannersAdaptor.setListData(banners!!)
                        bannersAdaptor.notifyDataSetChanged()
                    } else {
                        Log.d(TAG, "Not successful")
                    }
                } catch (e: HttpException) {
                    Log.d(TAG, "Exception ${e.message}")
                } catch (t: Throwable) {
                    Log.d(TAG, "Throwable ${t.cause}")
                }
            }
        }
    }

    // bind banners to the recycler view
    private fun setBanners() {
        bannersAdaptor = BannerAdapter()
        binding.bannersRecyclerView.adapter = bannersAdaptor
        binding.bannersRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }


    private fun setUserInfo() {
        if (user != null) {
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