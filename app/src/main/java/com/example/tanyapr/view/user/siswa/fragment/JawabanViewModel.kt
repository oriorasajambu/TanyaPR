/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.Jawaban
import com.example.tanyapr.model.NotificationUser
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch

class JawabanViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)

    private val _state : MutableLiveData<State<Jawaban>> = MutableLiveData()
    val state : LiveData<State<Jawaban>> = _state
    private val _notifState : MutableLiveData<State<NotificationUser>> = MutableLiveData()
    val notifState : LiveData<State<NotificationUser>> = _notifState

    fun fetchJawaban(idSoal: Int){
        viewModelScope.launch {
            try {
                val response = client.getJawaban(idSoal)
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()
                    data?.let { _state.value = State.Success(it) }
                }
                else _state.value = State.Fail(message = "Fail To Load Data")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

    fun sendNotification(field: Map<String, String>){
        viewModelScope.launch {
            try {
                val response = client.sendNotification(field)
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()
                    data?.let { _notifState.value = State.Success(it) }
                }
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_notifState)
            }
        }
    }

    fun updatePertanyaan(field: Map<String, String>){
        viewModelScope.launch {
            try{
                client.updatePertanyaan(field)
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_state)
            }
        }
    }

}