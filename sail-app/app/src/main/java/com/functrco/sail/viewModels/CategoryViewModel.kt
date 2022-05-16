package com.functrco.sail.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.functrco.sail.firebase_storage.FirebaseStorageDirectories
import com.functrco.sail.firebase_storage.FirebaseStorageManager
import com.functrco.sail.models.CategoryModel
import com.functrco.sail.repository.CategoriesRepository
import com.functrco.sail.sample_data.SampleCategories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(app: Application) : AndroidViewModel(app) {

    private var categories = MutableLiveData<List<CategoryModel>>()

    fun getObserver() = categories

    /* TODO: replace the code to fetch categories from API */
    fun fetchCategories(): MutableLiveData<List<CategoryModel>> {
        viewModelScope.launch {
            categories.postValue(CategoriesRepository().getAll())
        }

        return categories
    }

    fun init() {
        val sampleCategories = SampleCategories.getAll()

        sampleCategories.forEach { category ->
            viewModelScope.launch {
                val imageUri =
                    withContext(Dispatchers.Default) {
                        FirebaseStorageManager.uploadImage(
                            category.second,
                            FirebaseStorageDirectories.CATEGORIES,
                            getApplication<Application>().resources
                        )
                    }
                Log.d(TAG, "ImageUrl: $imageUri")
                CategoriesRepository().insert(
                    CategoryModel(category.first, imageUri)
                )
            }
        }
    }

    companion object {
        private val TAG = CategoryViewModel::class.java.name
    }

}




