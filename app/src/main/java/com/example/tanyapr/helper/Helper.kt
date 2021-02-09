/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tanyapr.model.User
import com.google.android.material.snackbar.Snackbar
import com.shasin.notificationbanner.Banner

class Helper {

    interface Refresh {
        fun onRefresh()
        fun setOnRefreshListener(x: Refresh){
            callback = x
        }
    }

    interface OnScrolledListener {
        fun performHide()
        fun performShow()
    }

    companion object {

        const val TOKEN = "TOKEN"
        const val TOKEN_USERNAME = "USERNAME"
        const val TOKEN_ROLE = "ROLE"
        const val TOKEN_NAMA_GURU = "NAMA_GURU"
        const val TOKEN_NAMA_SISWA = "NAMA_SISWA"
        const val TOKEN_COIN_GURU = "COIN_GURU"
        const val TOKEN_COIN_SISWA = "COIN_SISWA"
        const val TOKEN_ID_MATA_PELAJARAN = "ID_MATA_PELAJARAN"
        const val TOKEN_FCM = "FCM_TOKEN"

        var callback : Refresh? = null

        fun getToken(context: Context, token : String = TOKEN_USERNAME) : String? {
            val sharedPreferences = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
            return sharedPreferences.getString(token, "")
        }

        fun setToken(context: Context, user : User){
            val sharedPreferences = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
            sharedPreferences.edit().apply{
                putString(TOKEN_USERNAME, user.username)
                putString(TOKEN_ROLE, user.role)
                putString(TOKEN_NAMA_GURU, user.namaGuru)
                putString(TOKEN_NAMA_SISWA, user.namaSiswa)
                putString(TOKEN_COIN_GURU, user.coinGuru.toString())
                putString(TOKEN_COIN_SISWA, user.coinSiswa.toString())
                putString(TOKEN_ID_MATA_PELAJARAN, user.id_mata_pelajaran.toString())
                putString(TOKEN_ID_MATA_PELAJARAN, user.id_mata_pelajaran.toString())
                apply()
            }
        }

        fun clearToken(context: Context){
            val sharedPreferences = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
        }

        fun snackBar (rootView : View, message : String) {
            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
        }

        fun toast (context : Context, message : String){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun banner (rootView : View, context : Activity, type : Int, message : String){
            Banner.make(rootView, context, type, message, Banner.BOTTOM).show()
        }

        @Suppress("UNCHECKED_CAST")
        inline fun <viewModel: ViewModel> viewModelFactory(crossinline f: () -> viewModel) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    if(modelClass.isAssignableFrom(f()::class.java)) return f() as T
                    throw IllegalArgumentException("Unknown ViewModel")
                }
            }
    }

}