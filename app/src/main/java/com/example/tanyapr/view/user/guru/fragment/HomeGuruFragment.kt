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
import androidx.navigation.fragment.findNavController
import com.example.tanyapr.R
import com.example.tanyapr.databinding.HomeGuruFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.viewmodel.user.guru.fragment.HomeGuruViewModel

class HomeGuruFragment : Fragment() {

    companion object {
        fun newInstance() = HomeGuruFragment()
    }

    private lateinit var binding: HomeGuruFragmentBinding
    private lateinit var viewModel: HomeGuruViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = HomeGuruFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeGuruViewModel::class.java]

        val namaGuru = Helper.getToken(requireContext(), Helper.TOKEN_NAMA_GURU)
        val coin = Helper.getToken(requireContext(), Helper.TOKEN_COIN_GURU)
        coin?.let { binding.tvCoin.text = context?.getString(R.string.current_coin, it) }

        binding.pesan2.text = context?.getString(R.string.welcome_message, namaGuru)

        binding.btnJawabPr.setOnClickListener {
            findNavController().navigate(R.id.pertanyaan_guru)
        }
    }

}