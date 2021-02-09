/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.JawabanX
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmationDialogFragment : DialogFragment() {

    private val viewModel : JawabanViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val data = arguments?.let { bundle ->
            bundle.getSerializable("data") as JawabanX
        }
        val coin = arguments?.getInt("coin", 0)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Konfirmasi")
            .setMessage("Pilih Jawaban ${data?.username} Sebagai Jawaban Yang Benar?")
            .setNegativeButton("Cancel") { _, _, ->

            }
            .setPositiveButton("Confirm") { _, _, ->
                val field = mapOf(
                    "id_soal" to data!!.idSoal.toString(),
                    "id_solusi" to data.idSolusi.toString(),
                    "username" to data.username,
                    "coin" to coin.toString()
                )
                viewModel.updatePertanyaan(field)
                val field2 = mapOf(
                    "title" to "Kamu Mendapatkan Coin!",
                    "message" to "Hi ${data.username}! Kamu Telah Mendapatkan Koin Sebesar $coin\nYuk Jawab Pertanyaan Siswa Yang Lain!",
                    "username" to data.username
                )
                viewModel.sendNotification(field2)
                Helper.callback?.onRefresh()
            }
            .show()
    }

}