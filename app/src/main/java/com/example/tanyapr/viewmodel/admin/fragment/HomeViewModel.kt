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
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.network.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel : ViewModel() {

    private val repo = Repository()
    private val homeState : MutableLiveData<HomeState> = MutableLiveData()
    fun getHomeState() = homeState

    fun fetchData(){
        viewModelScope.launch{
            homeState.value = HomeState.Loading(true)
            try {
                delay(1500)
                val response = repo.getData("pelajaran")
                if(response.isSuccessful){
                    val data = response.body()?.data
                    if(data != null) homeState.value = HomeState.Success(data)
                    else homeState.value = HomeState.Fail("Data Kosong!")
                }
            }
            catch (throwable : Throwable){
                handleError(throwable)
            }
            finally {
                homeState.value = HomeState.Loading(false)
            }
        }
    }

    fun updateData(field: List<String>) {
        homeState.value = HomeState.Loading(true)
        viewModelScope.launch {
            try {
                val response = repo.updatePelajaran(field)
//                if (response.isSuccessful) homeState.value = HomeState.Success()
            }
            catch (throwable: Throwable){
                handleError(throwable)
            }
            finally {
                homeState.value = HomeState.Loading()
            }
        }
    }

    fun deleteData(id: Int){
        homeState.value = HomeState.Loading(true)
        viewModelScope.launch {
            try {
                val response = repo.hapusPelajaran(id)
//                if (response.isSuccessful) homeState.value = HomeState.Success()
            }
            catch (throwable: Throwable){
                handleError(throwable)
            }
            finally {
                homeState.value = HomeState.Loading()
            }
        }
    }

    fun insertData(field: Map<String, String>){
        viewModelScope.launch {
            try {
                val response = repo.insertPelajaran(field)
//                if (response.isSuccessful) homeState.value = HomeState.Success(null)
            }
            catch (throwable: Throwable){
                handleError(throwable)
            }
        }
    }

    private fun handleError(throwable: Throwable){
        when(throwable){
            is HttpException -> {
                Log.e("Error", throwable.message())
                homeState.value = HomeState.Error("Connection Time Out!")
            }
            is IOException -> {
                Log.e("Error", throwable.message.toString())
                homeState.value = HomeState.Error("Not Connected To Internet")
            }
            else -> {
                Log.e("Error", throwable.message.toString())
                homeState.value = HomeState.Error("Unknown Error!")
            }
        }
    }

    sealed class HomeState{
        data class Loading(val isLoading : Boolean = false) : HomeState()
        data class Success(val data : List<Pelajaran>) : HomeState()
        data class Fail(val message : String) : HomeState()
        data class Error(val message : String) : HomeState()
    }
}