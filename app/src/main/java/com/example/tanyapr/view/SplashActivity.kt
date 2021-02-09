/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tanyapr.databinding.ActivitySplashBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.view.admin.activity.AdminActivity
import com.example.tanyapr.view.user.guru.activity.GuruActivity
import com.example.tanyapr.view.user.siswa.activity.SiswaActivity
import com.example.tanyapr.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel : SplashViewModel
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Helper.viewModelFactory {
            SplashViewModel()
        })[SplashViewModel::class.java]

        binding.animation.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                viewModel.updateUi()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })

        viewModel.getIsFinished().observe(this, {
            val intent = when(Helper.getToken(this, Helper.TOKEN_ROLE)){
                "admin" -> Intent(this, AdminActivity::class.java)
                "guru" -> Intent(this, GuruActivity::class.java)
                "siswa" -> Intent(this, SiswaActivity::class.java)
                else -> Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        })
    }
}