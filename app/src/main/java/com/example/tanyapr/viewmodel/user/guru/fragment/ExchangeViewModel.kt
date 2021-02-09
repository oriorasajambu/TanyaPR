/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.user.guru.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.Request
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)

    private val _state : MutableLiveData<State<Request>> = MutableLiveData()
    val state : LiveData<State<Request>> = _state

    fun validate(coin: String, coinNow: String, vararg field: String) : Boolean{
        _state.value = State.Reset()
        var condition = 1
        field.forEach {
            if(it.isEmpty()){
                _state.value = State.Invalid(message = "Tidak Boleh Kosong", condition)
                return false
            }
            condition++
        }
        if(coin.toInt() > coinNow.toInt()) {
            _state.value = State.Invalid(message = "Coin Tidak Cukup", condition)
            return false
        }
        return true
    }

    fun redeem(field: Map<String, String>){
        viewModelScope.launch {
            try {
                val response = client.redeem(field)
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()
                    data?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Gagal Redeem")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

    fun sendNotification(field: Map<String, String>){
        viewModelScope.launch {
            try {
                client.sendNotification(field)
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }
}