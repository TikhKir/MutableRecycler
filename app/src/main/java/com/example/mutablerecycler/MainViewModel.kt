package com.example.mutablerecycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val listLiveData = MutableLiveData<List<Int>>()
    private val baseList = IntRange(1, 15).toMutableList()
    private val deletedPull = arrayListOf<Int>()
    private var counter = baseList.size

    init {
        listLiveData.postValue(baseList)
        insertElement()
    }

    private fun insertElement() {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 0..Int.MAX_VALUE) {
                delay(5000)
                val randomPosition = Random.nextInt(baseList.size + 1) // inserting at any position included new position (+1)
                if (deletedPull.isEmpty()) {
                    counter++
                    baseList.add(randomPosition, counter)
                } else {
                    baseList.add(randomPosition, deletedPull.last())
                    deletedPull.remove(deletedPull.last())
                }
                listLiveData.postValue(baseList)
            }
        }
    }

    fun getLiveData(): LiveData<List<Int>> {
        return listLiveData
    }

    fun deleteElement(element: Int) {
        if (baseList.remove(element)) deletedPull.add(element) //check for exclude double inserting in deletePull
        listLiveData.postValue(baseList)
    }


}