/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.guru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tanyapr.R
import com.example.tanyapr.databinding.ActivityGuruBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.view.LoginActivity
import com.example.tanyapr.viewmodel.user.guru.activity.GuruViewModel
import com.google.firebase.messaging.FirebaseMessaging

class GuruActivity : AppCompatActivity(), Helper.OnScrolledListener {

    override fun performHide() = binding.bottomAppBar.performHide()

    override fun performShow() = binding.bottomAppBar.performShow()

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var viewModel : GuruViewModel
    private lateinit var binding : ActivityGuruBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuruBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GuruViewModel::class.java]

        setSupportActionBar(binding.toolBar)
        binding.tvTitle.text = getString(R.string.sapa, Helper.getToken(this, Helper.TOKEN_NAMA_GURU))
        binding.bottomNavigationView.background = null

        navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.navController)

        binding.btnFab.setOnClickListener {
            navHostFragment.navController.navigate(R.id.exchangeFragment)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_guru_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                val idMataPelajaran = Helper.getToken(this, Helper.TOKEN_ID_MATA_PELAJARAN)
                idMataPelajaran?.let {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(it)
                }
                Helper.clearToken(this)
                Intent(this, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(this)
                    ActivityCompat.finishAffinity(this@GuruActivity)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}