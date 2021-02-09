/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.user.siswa.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.TopUp
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch

class HistoryTopUpViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)
    private val _state : MutableLiveData<State<List<TopUp>>> = MutableLiveData()
    val state : LiveData<State<List<TopUp>>> = _state

    fun getTopUp(username: String){
        viewModelScope.launch {
            try {
                val response = client.getTopUp(username)
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()?.data
                    data?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Fail To Load Data")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

}