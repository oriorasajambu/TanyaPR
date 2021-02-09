/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tanyapr.databinding.HomeBottomFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.viewmodel.admin.fragment.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class HomeBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: HomeBottomFragmentBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeBottomFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = arguments?.let { bundle ->
            val data = bundle.getSerializable("data") as Pelajaran
            binding.tieKodePelajaran.setText(data.mataPelajaran)
            binding.tieNamaPelajaran.setText(data.namaMataPelajaran)
            binding.tieKoin.setText(data.coin)
            data.idMataPelajaran
        }

        binding.btnEdit.setOnClickListener {
            id?.let {
                val kodePelajaran = binding.tieKodePelajaran.text.toString().trim().toUpperCase(Locale.ROOT)
                val namaPelajaran = binding.tieNamaPelajaran.text.toString().trim().capitalize(Locale.ROOT)
                val koin = binding.tieKoin.text.toString().trim()
                val field = listOf(it, kodePelajaran, namaPelajaran, koin)
                viewModel.updateData(field)
                Helper.callback?.onRefresh()
                dismiss()
            }
        }

    }

}