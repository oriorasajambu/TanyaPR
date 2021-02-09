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
import com.example.tanyapr.model.NotificationUser
import com.example.tanyapr.model.Pertanyaan
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch

class PertanyaanGuruViewModel : ViewModel() {
    private val client = RestClient.create(AccesPoint.endPoint)

    private val _pertanyaanState: MutableLiveData<State<List<Pertanyaan>>> = MutableLiveData()
    val pertanyaanState : LiveData<State<List<Pertanyaan>>> = _pertanyaanState
    private val _state: MutableLiveData<State<NotificationUser>> = MutableLiveData()
    val state: LiveData<State<NotificationUser>> = _state

    fun getPertanyaan(id_mata_pelajaran: Int) {
        viewModelScope.launch {
            try {
                val response = client.getPertanyaan(id_mata_pelajaran = id_mata_pelajaran)
                if(response.isSuccessful && response.body() != null){
                    val data = response.body()?.data
                    data?.let { _pertanyaanState.value = State.Success(it) }
                }
                else _pertanyaanState.value = State.Fail(message = "Fail To Load")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(_pertanyaanState)
            }
        }
    }


}