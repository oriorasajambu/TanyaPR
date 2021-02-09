/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tanyapr.R
import com.example.tanyapr.databinding.ActivityMainBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.viewmodel.MainViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this,
            Helper.viewModelFactory { MainViewModel() }
        )[MainViewModel::class.java]

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful) Log.i("FCM KEY", it.result.toString())
        }

        FirebaseMessaging.getInstance().subscribeToTopic("13")

    }
}