/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tanyapr.R
import com.example.tanyapr.databinding.SettingSiswaFragmentBinding
import com.example.tanyapr.viewmodel.user.siswa.fragment.SettingSiswaViewModel

class SettingSiswaFragment : Fragment() {

    companion object {
        fun newInstance() = SettingSiswaFragment()
    }

    private lateinit var binding: SettingSiswaFragmentBinding
    private lateinit var viewModel: SettingSiswaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = SettingSiswaFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingSiswaViewModel::class.java)

    }

}