/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tanyapr.R
import com.example.tanyapr.databinding.ActivitySiswaBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.view.LoginActivity
import com.example.tanyapr.viewmodel.user.siswa.activity.SiswaViewModel

class SiswaActivity : AppCompatActivity(), Helper.OnScrolledListener {

    override fun performHide() {
        binding.bottomAppBar.performHide()
        binding.btnFab.hide()
    }

    override fun performShow() {
        binding.bottomAppBar.performShow()
        binding.btnFab.show()
    }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var viewModel : SiswaViewModel
    private lateinit var binding : ActivitySiswaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SiswaViewModel::class.java]

        setSupportActionBar(binding.toolBar)
        binding.tvTitle.text = getString(R.string.sapa, Helper.getToken(this, Helper.TOKEN_NAMA_SISWA))
        binding.bottomNavigationView.background = null

        navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.navController)

        binding.btnFab.setOnClickListener {
            navHostFragment.navController.navigate(R.id.insertTopUpFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                Helper.clearToken(this)
                Intent(this, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(this)
                    ActivityCompat.finishAffinity(this@SiswaActivity)
                }
                return true
            }
            R.id.setting -> {
                navHostFragment.navController.navigate(R.id.settingSiswaFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}