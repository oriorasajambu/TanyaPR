/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.User
import com.example.tanyapr.network.Repository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = Repository()
    private val state : MutableLiveData<User> = MutableLiveData()
    fun getData() = state

    fun login(){
        viewModelScope.launch {

            try{
                val response = repo.getLogin("frozenfly", "123456789")
                if(response.isSuccessful){
                    val user = response.body()?.data
                    user?.let {
                        state.value = it
                    }
                }
            }
            catch (throwable : Throwable){

            }

        }
    }

}