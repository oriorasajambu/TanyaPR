/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tanyapr.R
import com.example.tanyapr.databinding.HomeSiswaFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.tools.getToken
import com.example.tanyapr.viewmodel.user.siswa.activity.SiswaViewModel

class HomeSiswaFragment : Fragment() {

    companion object {
        fun newInstance() = HomeSiswaFragment()
    }

    private lateinit var binding: HomeSiswaFragmentBinding
    private val viewModel: SiswaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeSiswaFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val namaSiswa = context?.getToken(Helper.TOKEN_NAMA_SISWA)
        val coin = context?.getToken(Helper.TOKEN_COIN_SISWA)
        coin?.let {
            binding.btnTanyaPr.isEnabled = it.toInt() >= 1200
            binding.tvCoin.text = context?.getString(R.string.current_coin, it)
        }
        binding.pesan2.text = context?.getString(R.string.welcome_message, namaSiswa)

        binding.btnTanyaPr.setOnClickListener {
            findNavController().navigate(R.id.insertPertanyaanFragment)
        }

        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_homeSiswaFragment_to_pertanyaanFragment)
        }
    }

}