/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tanyapr.R
import com.example.tanyapr.databinding.ActivityAdminBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.view.LoginActivity
import com.example.tanyapr.view.admin.tools.HomeInsertFragment
import com.example.tanyapr.viewmodel.admin.activity.AdminViewModel

class AdminActivity : AppCompatActivity(), Helper.OnScrolledListener {

    override fun performHide() {
        binding.bottomAppBar.performHide()
    }

    override fun performShow() {
        binding.bottomAppBar.performShow()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                Log.i("Logout", "Logout Clicked!")
                Helper.clearToken(this)
                Intent(this, LoginActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(this)
                    ActivityCompat.finishAffinity(this@AdminActivity)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private lateinit var viewModel : AdminViewModel
    private lateinit var binding : ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        setSupportActionBar(binding.toolBar)
        binding.tvTitle.text = getString(R.string.title_page, Helper.getToken(this))
        binding.bottomNavigationView.background = null

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.navController)

        binding.btnFab.setOnClickListener {
            HomeInsertFragment.newInstance().apply {
                show(supportFragmentManager, HomeInsertFragment.TAG)
            }
        }
    }
}