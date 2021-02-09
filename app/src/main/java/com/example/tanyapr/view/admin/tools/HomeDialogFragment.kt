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
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.viewmodel.admin.fragment.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeDialogFragment : DialogFragment(){

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val data = arguments?.let { bundle ->
            bundle.getSerializable("data") as Pelajaran
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi")
            .setMessage("Hapus data ${data?.namaMataPelajaran}?")
            .setNegativeButton("Cancel") { _, _ ->

            }
            .setPositiveButton("Hapus") { _, _ ->
                data?.let { viewModel.deleteData(it.idMataPelajaran.toInt()) }
                Helper.callback?.onRefresh()
            }
            .show()
    }

}