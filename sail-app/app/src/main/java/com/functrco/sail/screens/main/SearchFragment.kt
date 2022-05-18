package com.functrco.sail.screens.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.functrco.sail.MainActivity
import com.functrco.sail.ProductPage
import com.functrco.sail.databinding.FragmentSearchBinding
import com.functrco.sail.utils.GridSpaceItemDecoration
import com.functrco.sail.adaptors.ProductAdaptor
import com.functrco.sail.models.ProductModel
import com.functrco.sail.viewModels.ProductViewModel
import com.functrco.sail.utils.Util
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private val productsAdaptor = ProductAdaptor()
    private var products = listOf<ProductModel>()

    private var mInterstitialAd: InterstitialAd? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        val categoryName: String? = arguments?.getString("category_name")
        if(categoryName != null){
            binding.searchProduct.setQuery(categoryName, false)
        }

        // setup AdmobAd
        setupAdmobAd()

        setSearchView()

        setProducts()
        observeProducts()

        return binding.root
    }

    private fun isProductMatched(query: String, product: ProductModel): Boolean {
        return product.category?.name?.contains(query, ignoreCase = true) == true ||
                product.brandName?.contains(query, ignoreCase = true) == true ||
                product.name?.contains(query, ignoreCase = true) == true ||
                product.description?.contains(query, ignoreCase = true) == true ||
                product.price?.toString()?.contains(query, ignoreCase = true) == true
    }

    private fun doSearch(query: String?) {
        Log.d(TAG, "Query is: $query")

        if (query != null && query.isNotEmpty()) {
            val newProducts = mutableListOf<ProductModel>()
            products.forEach { product ->
                if(isProductMatched(query, product)){
                    newProducts.add(product)
                }
            }
            productsAdaptor.setListData(newProducts)
            productsAdaptor.notifyDataSetChanged()

        }
        else{
            productsAdaptor.setListData(products)
            productsAdaptor.notifyDataSetChanged()
        }
    }

    private fun setSearchView() {
        binding.searchProduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                doSearch(query)
                return true
            }
        })
    }

    private fun setupAdmobAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this.requireContext(),"ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }


    // bind categories to the recycler view
    private fun setProducts() {
        binding.productsRecyclerView.adapter = productsAdaptor
        binding.productsRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.productsRecyclerView.addItemDecoration(
            GridSpaceItemDecoration(
                2,
                0F,
                Util.dp2dx(10F, resources)
            )
        )

        productsAdaptor.onItemClick = {
            val redirectToProductPage = Intent(activity, ProductPage::class.java)
            redirectToProductPage.putExtra("product_info", Util.toSerializable(it))
            startActivity(redirectToProductPage)

            // show ad
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this.requireActivity())
            } else {
                Log.d(TAG, "The interstitial ad wasn't ready yet.")
            }
        }
    }

    // set observer on the categories list and fetch categories
    @SuppressLint("NotifyDataSetChanged")
    private fun observeProducts() {
        productViewModel.getObserver().observe(viewLifecycleOwner, Observer {
            productsAdaptor.setListData(it)
            products = it
            productsAdaptor.notifyDataSetChanged()
            doSearch(binding.searchProduct.query.toString())
        })
        productViewModel.getAll()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "ProductFragment"
    }
}