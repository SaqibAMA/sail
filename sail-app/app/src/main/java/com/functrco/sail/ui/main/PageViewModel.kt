package com.functrco.sail.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    val SCREEN_BACKGROUND = listOf("#6FCF97", "#F2994A", "#20A3CD")
    val SCREEN_HEADING = listOf("Community", "Ease", "Accessible")
    val SCREEN_DESC = listOf(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )

    private val _index = MutableLiveData<Int>()
    val text: LiveData<List<String>> = Transformations.map(_index) {
        listOf(SCREEN_HEADING[it], SCREEN_DESC[it], SCREEN_BACKGROUND[it])
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}