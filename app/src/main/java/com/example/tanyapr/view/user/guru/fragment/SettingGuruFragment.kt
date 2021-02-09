/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.guru.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tanyapr.databinding.SettingGuruFragmentBinding
import com.example.tanyapr.viewmodel.user.guru.fragment.SettingGuruViewModel

class SettingGuruFragment : Fragment() {

    companion object {
        fun newInstance() = SettingGuruFragment()
    }

    private lateinit var binding: SettingGuruFragmentBinding
    private lateinit var viewModel: SettingGuruViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = SettingGuruFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingGuruViewModel::class.java]

    }

}