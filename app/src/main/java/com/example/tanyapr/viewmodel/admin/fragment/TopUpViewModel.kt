/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.admin.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.TopUp
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TopUpViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)
    private val state : MutableLiveData<State<List<TopUp>>> = MutableLiveData()
    fun getState() = state

    fun getTopUp(){
        viewModelScope.launch {
            try {
                val response = client.getTopUp()
                if(response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    data?.let { state.value = State.Success(data) }
                }
                else state.value = State.Fail(message = "Data Kosong!")
            }
            catch (throwable : Throwable){
                handleError(throwable)
            }
        }
    }

    private fun handleError(throwable: Throwable){
        when(throwable){
            is HttpException -> {
                Log.e("HttpException", throwable.message())
                state.value = State.Error(message = "Connection Timeout!")
            }
            is IOException -> {
                Log.e("IOException", throwable.message.toString())
                state.value = State.Error(message = "Koneksi Internet Tidak Ada!")
            }
            else -> {
                Log.e("Unknow Error", throwable.message.toString())
                state.value = State.Error(message = "Unknown Error")
            }
        }
    }

}