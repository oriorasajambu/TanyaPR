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
import com.example.tanyapr.model.Guru
import com.example.tanyapr.model.Siswa
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UserViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)
    private val state :  MutableLiveData<State> = MutableLiveData()
    fun getState() = state

    fun fetchDataGuru(){
        viewModelScope.launch {
            try {
                val responseGuru = client.getDataGuru("guru")
                if(responseGuru.isSuccessful){
                    val data = responseGuru.body()?.data
                    data?.let { state.value = State.SuccessGuru(it) }
                }
            }
            catch (throwable : Throwable){
                handleError(throwable)
            }
        }
    }

    fun fetchDataSiswa(){
        viewModelScope.launch {
            try {
                val responseGuru = client.getDataSiswa("siswa")
                if(responseGuru.isSuccessful){
                    val data = responseGuru.body()?.data
                    data?.let { state.value = State.SuccessSiswa(it) }
                }
                else state.value = State.Fail("Data Kosong!")
            }
            catch (throwable : Throwable){
                handleError(throwable)
            }
        }
    }

    fun searchData(){
        viewModelScope.launch {
            try {
                var responseSiswa = client.getSiswaCredential("siswa", "oriorasajambu")
                var responseGuru = client.getGuruCredential("guru", "frozenfly")
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
                state.value = State.Error("Connection Timeout!")
            }
            is IOException -> {
                Log.e("IOException", throwable.message.toString())
                state.value = State.Error("Koneksi Internet Tidak Ada!")
            }
            else -> {
                Log.e("Unknow Error", throwable.message.toString())
                state.value = State.Error("Unknown Error")
            }
        }
    }

    sealed class State {
        data class SuccessGuru(val data: List<Guru>) : State()
        data class SuccessSiswa(val data: List<Siswa>) : State()
        data class Fail (val message : String) : State()
        data class Error (val message : String) : State()
        //object Reset : State()
    }

}