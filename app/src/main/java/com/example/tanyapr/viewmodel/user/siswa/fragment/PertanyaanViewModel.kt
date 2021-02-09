/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.user.siswa.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.Pertanyaan
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch

class PertanyaanViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)

    private val _pertanyaanState: MutableLiveData<State<List<Pertanyaan>>> = MutableLiveData()
    val pertanyaanState = _pertanyaanState

    fun getPertanyaan(username: String){
        viewModelScope.launch {
            try{
                val response = client.getPertanyaan(username)
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