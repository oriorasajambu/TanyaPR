/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.viewmodel.admin.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanyapr.model.Request
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)
    private val state : MutableLiveData<State<Request>> = MutableLiveData()
    fun getState() = state

    fun updateAdmin(field: Map<String, String>){
        viewModelScope.launch {
            try{
                val response = client.updateAdmin(field)
                if(response.isSuccessful && response.body() != null){
                    val request = response.body()
                    request?.let {
                        if(request.status == 1) state.value = State.Success(it)
                    }
                }
                else state.value = State.Fail(message = "Gagal Update Username Dan Password")
            }
            catch (throwable : Throwable){
                throwable.handleThrowable(state)
            }
        }
    }

    fun validate(field: Map<String, String>, confirmPassword: String) : Boolean{
        if((field["username"] ?: error("")).isEmpty()) {
            state.value = State.Invalid("Username Tidak Boleh Kosong!", 1)
            return false
        }
        if((field["new_username"] ?: error("")).isEmpty()) {
            state.value = State.Invalid("Username Yang Baru Tidak Boleh Kosong!", 2)
            return false
        }
        if((field["password"] ?: error("")).isEmpty()) {
            state.value = State.Invalid("Password Tidak Boleh Kosong!", 3)
            return false
        }
        if((field["new_password"] ?: error("")).isEmpty()) {
            state.value = State.Invalid("Password Yang Baru Tidak Boleh Kosong!", 4)
            return false
        }
        if((field["new_password"] ?: error("") != confirmPassword)) {
            state.value = State.Invalid("Password Baru Tidak Sama Dengan Konfirmasi Password", 5)
            return false
        }
        return true
    }

}