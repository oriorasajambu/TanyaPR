/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val isFinished : MutableLiveData<Boolean> = MutableLiveData()
    fun getIsFinished() = isFinished

    fun updateUi(isFinish : Boolean = true){
        viewModelScope.launch(Dispatchers.Main) {
            isFinished.value = isFinish
        }
    }

}