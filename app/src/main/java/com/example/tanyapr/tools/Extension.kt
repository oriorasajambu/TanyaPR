/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.tools

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.view.admin.tools.State
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

fun <T> Throwable.handleThrowable (state: MutableLiveData<State<T>>) {
    when(this){
        is HttpException -> {
            Log.e("HttpException", this.message())
            state.value = State.Error(message = "Connection Timeout!")
        }
        is IOException -> {
            Log.e("IOException", this.message.toString())
            state.value = State.Error(message = "Koneksi Internet Tidak Ada!")
        }
        else -> {
            Log.e("Unknown Error", this.message.toString())
            state.value = State.Error(message = "Unknown Error")
        }
    }
}

fun String.createRequestBody() : RequestBody {
    return RequestBody.create(MultipartBody.FORM, this)
}

fun File.createFileBody(): MultipartBody.Part {
    return this.let {
        MultipartBody.Part.createFormData(
                "file",
                this.name,
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        this
                )
        )
    }
}

fun TextInputEditText.getTrimmedText() : String = this.text.toString().trim()

fun Context.getToken(token : String = Helper.TOKEN_USERNAME) : String? {
    val sharedPreferences = this.getSharedPreferences(Helper.TOKEN, Context.MODE_PRIVATE)
    return sharedPreferences.getString(token, "")
}

fun Context.setTokenCoin(token: String, Coin: Int){
    val sharedPreferences = this.getSharedPreferences(Helper.TOKEN, Context.MODE_PRIVATE)
    sharedPreferences.edit().apply{
        putString(token, Coin.toString())
        apply()
    }
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
