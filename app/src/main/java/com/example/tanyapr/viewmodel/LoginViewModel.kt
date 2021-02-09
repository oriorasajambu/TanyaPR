/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.User
import com.example.tanyapr.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.nio.channels.NotYetConnectedException

class LoginViewModel : ViewModel() {

    private val repo = Repository()
    private val state : MutableLiveData<LoginState> = MutableLiveData()
    fun getState() = state

    fun login(username: String, password: String){
        state.value = LoginState.Reset
        viewModelScope.launch {
            state.value = LoginState.Loading(true)
            try {
                val response = repo.getLogin(username, password)
                if(response.isSuccessful){
                    val data = response.body()?.data
                    if(data != null) state.value = LoginState.Success(data)
                    else state.value = LoginState.Fail("Username / Password Salah")
                }
            }
            catch (throwable : Throwable){
                handleError(throwable)
            }
            finally {
                state.value = LoginState.Loading()
            }
        }
    }

    fun isValid(username: String, password: String) : Boolean{
        state.value = LoginState.Reset
        if(username.isEmpty()){
            state.value = LoginState.Invalid("Username Tidak Boleh Kosong!")
            return false
        }
        else if(password.isEmpty()){
            state.value = LoginState.Invalid(password = "Password Tidak Boleh Kosong!")
            return false
        }
        return true
    }

    private fun handleError(throwable: Throwable){
        when(throwable){
            is HttpException -> state.value = LoginState.Error("Connection Time Out!")
            is IOException -> state.value = LoginState.Error("Not Connected To Internet")
            else -> state.value = LoginState.Error("Unknown Error!")
        }
    }

    sealed class LoginState {
        data class Loading(val isLoading : Boolean = false) : LoginState()
        data class Success(val user: User) : LoginState()
        data class Fail(val message : String) : LoginState()
        data class Invalid(val username : String? = null, val password : String? = null) : LoginState()
        data class Error(val message: String) : LoginState()
        object Reset : LoginState()
    }

}