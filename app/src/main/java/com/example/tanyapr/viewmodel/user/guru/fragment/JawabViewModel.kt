/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.user.guru.fragment

import android.util.Log
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
import okhttp3.MultipartBody
import okhttp3.RequestBody

class JawabViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)

    private val _state : MutableLiveData<State<Request>> = MutableLiveData()
    val state : LiveData<State<Request>> = _state

    fun sendNotification(field: Map<String, String>){
        viewModelScope.launch {
            val response = client.sendNotification(field)
            if(response.isSuccessful && response.body() != null){
                val data = response.body()?.success
                data?.let{ Log.i("Success", it.toString()) }
            }
        }
    }

    fun validate(vararg value: String) : Boolean {
        _state.value = State.Reset()
        var condition = 1
        value.forEach {
            if(it.isEmpty() || it == "null") {
                _state.value = State.Invalid(message = "Tidak Boleh Kosong", condition)
                return false
            }
            condition++
        }
        return true
    }

    fun insertJawaban(field: Map<String, RequestBody>, file: MultipartBody.Part){
        viewModelScope.launch {
            try {
                val response = client.insertSolusi(field, file)
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()
                    data?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Fail To Upload File")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }
}