package com.example.mutablerecycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val listLiveData = MutableLiveData<List<Int>>()
    private val baseList = IntRange(1, 15).toList() as ArrayList
    private val deletedPool = arrayListOf<Int>()
    private var counter = baseList.size

    init {
        listLiveData.postValue(baseList)
        insertElement()
    }

    private fun insertElement() {
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 0..Int.MAX_VALUE) {
                delay(5000)
                val randomPosition = Random.nextInt(baseList.size + 1)
                if (deletedPool.size == 0) {
                    counter++
                    baseList.add(randomPosition, counter)
                } else {
                    baseList.add(randomPosition, deletedPool.last())
                    deletedPool.remove(deletedPool.last())
                }
                listLiveData.postValue(baseList)
            }
        }
    }

    fun getLiveData(): LiveData<List<Int>> {
        return listLiveData
    }

    fun deleteElement(element: Int) {
        if (baseList.remove(element = element)) deletedPool.add(element)
        listLiveData.postValue(baseList)
    }


}