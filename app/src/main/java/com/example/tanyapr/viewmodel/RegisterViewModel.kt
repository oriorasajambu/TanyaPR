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
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.model.Request
import com.example.tanyapr.network.AccesPoint
import com.example.tanyapr.network.RestClient
import com.example.tanyapr.tools.handleThrowable
import com.example.tanyapr.view.admin.tools.State
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel : ViewModel() {

    private val client = RestClient.create(AccesPoint.endPoint)
    private val state : MutableLiveData<State<List<Pelajaran>>> = MutableLiveData()
    private val registerStateGuru : MutableLiveData<State<Request>> = MutableLiveData()
    private val registerStateSiswa : MutableLiveData<State<Request>> = MutableLiveData()
    fun getRegisterStateGuru() = registerStateGuru
    fun getRegisterStateSiswa() = registerStateSiswa
    fun getState() = state

    fun fetchData(){
        viewModelScope.launch{
            try {
                val response = client.getData("pelajaran")
                if(response.isSuccessful){
                    val data = response.body()?.data
                    if(data != null) state.value = State.Success(data)
                    else state.value = State.Fail(message = "Data Kosong!")
                }
            }
            catch (throwable : Throwable){
                throwable.handleThrowable(state)
            }
        }
    }

    fun insertGuru(field: Map<String, RequestBody>, file: MultipartBody.Part){
        viewModelScope.launch {
            try{
                val response = client.insertGuru(field, file)
                if(response.isSuccessful) response.body()?.let { registerStateGuru.value = State.Success(it) }
                else registerStateGuru.value = State.Fail(message = "Fail To Register")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(registerStateGuru)
            }
        }
    }

    fun insertSiswa(field: Map<String, String>) {
        viewModelScope.launch {
            try{
                val response = client.insertSiswa(field)
                if(response.isSuccessful) response.body()?.let { registerStateSiswa.value = State.Success(it) }
                else registerStateSiswa.value = State.Fail(message = "Fail To Register")
            }
            catch (throwable: Throwable){
                throwable.handleThrowable(registerStateSiswa)
            }
        }
    }

    fun validateFormGuru(formData: List<String>): Boolean {
        registerStateGuru.value = State.Reset()
        if(formData[0].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Username", condition = 0)
            return false
        }
        if(formData[1].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Password", condition = 1)
            return false
        }
        if(formData[2].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Confirm Password", condition = 2)
            return false
        }
        if(formData[3].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Nama Lengkap", condition = 3)
            return false
        }
        if(formData[4].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Universitas", condition = 4)
            return false
        }
        if(formData[5].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Semester", condition = 5)
            return false
        }
        if(formData[6].isEmpty()){
            registerStateGuru.value = State.Invalid(message = "Upload", condition = 6)
            return false
        }
        return true
    }

    fun validateFormSiswa(formData: List<String>): Boolean {
        registerStateSiswa.value = State.Reset()
        if(formData[0].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Username", condition = 0)
            return false
        }
        if(formData[1].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Password", condition = 1)
            return false
        }
        if(formData[2].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Confirm Password", condition = 2)
            return false
        }
        if(formData[1] != formData[2]){
            registerStateSiswa.value = State.Invalid(message = "Confirm Password", condition = 7)
            return false
        }
        if(formData[3].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Nama Lengkap", condition = 3)
            return false
        }
        if(formData[4].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Sekolah", condition = 4)
            return false
        }
        if(formData[5].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Kelas", condition = 5)
            return false
        }
        if(formData[6].isEmpty()){
            registerStateSiswa.value = State.Invalid(message = "Email", condition = 6)
            return false
        }
        if(!formData[6].validEmail()){
            registerStateSiswa.value = State.Invalid(message = "Email", condition = 8)
            return false
        }
        return true
    }



}