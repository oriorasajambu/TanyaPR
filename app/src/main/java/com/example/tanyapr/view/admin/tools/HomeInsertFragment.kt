/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.tools

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.tanyapr.databinding.HomeInsertFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.viewmodel.admin.fragment.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class HomeInsertFragment : DialogFragment() {

    companion object {
        val TAG :String = HomeInsertFragment::class.java.simpleName
        fun newInstance() = HomeInsertFragment()
    }

    private lateinit var binding: HomeInsertFragmentBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = HomeInsertFragmentBinding.inflate(requireActivity().layoutInflater)
        val builder = MaterialAlertDialogBuilder(requireContext()).apply {
            setView(binding.root)
            setTitle("Tambah Pelajaran")
            setPositiveButton("Tambah"){ _, _ ->
                val kodeMataPelajaran = binding.tieKodePelajaran.text.toString().trim().toUpperCase(Locale.ROOT)
                val namaMataPelajaran = binding.tieNamaPelajaran.text.toString().trim().capitalize(Locale.ROOT)
                val koin = binding.tieKoin.text.toString().trim()
                val field = mapOf(
                        "mata_pelajaran" to kodeMataPelajaran,
                        "nama_mata_pelajaran" to namaMataPelajaran,
                        "coin" to koin
                )
                viewModel.insertData(field)
                Helper.callback?.onRefresh()
            }
            setNegativeButton("Cancel"){ _, _ ->

            }
        }
        return builder.create()
    }

}