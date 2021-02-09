/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.user.siswa.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.model.Request
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SiswaViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)

    private val _pelajaranState: MutableLiveData<State<List<Pelajaran>>> = MutableLiveData()
    val pelajaranState : LiveData<State<List<Pelajaran>>> = _pelajaranState

    private val _state: MutableLiveData<State<Request>> = MutableLiveData()
    val state: LiveData<State<Request>> = _state

    fun validate(vararg value: String) : Boolean {
        _state.value = State.Reset()
        var condition = 1
        value.forEach {
            if(it.isEmpty()) {
                _state.value = State.Invalid(message = "Tidak Boleh Kosong", condition)
                return false
            }
            condition++
        }
        return true
    }

    fun insertTopUp(field: Map<String, RequestBody>, file: MultipartBody.Part){
        viewModelScope.launch {
            try {
                val response = client.insertTopUp(field, file)
                if(response.isSuccessful && response.body() != null) {
                    val request = response.body()
                    request?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Fail To Top Up")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

    fun insertPertanyaan(field: Map<String, RequestBody>, file: MultipartBody.Part){
        viewModelScope.launch {
            try {
                val response = client.insertPertanyaan(field, file)
                if(response.isSuccessful && response.body() != null) {
                    val request = response.body()
                    request?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Fail To Upload File")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

    fun getMataPelajaran(){
        viewModelScope.launch {
            try{
                val response = client.getData("pelajaran")
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()?.data
                    data?.let { _pelajaranState.value = State.Success(it) }
                }
                else _pelajaranState.value = State.Fail(message = "Data Kosong")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_pelajaranState)
            }
        }
    }

    fun updateCoin(username: String, coinValue: String){
        viewModelScope.launch {
            try{
                val response = client.updateCoinSiswa(username, coinValue)
                if(response.isSuccessful && response.body() != null){
                    val request = response.body()
                    request?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Fail To Update Coin")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

    fun sendNotification(field: Map<String, String>){
        viewModelScope.launch {
            val response = client.sendNotificationTopic(field)
            if(response.isSuccessful && response.body() != null) {
                val data = response.body()?.messageId
                data?.let { Log.i("Message ID", it.toString()) }
            }
        }
    }
}