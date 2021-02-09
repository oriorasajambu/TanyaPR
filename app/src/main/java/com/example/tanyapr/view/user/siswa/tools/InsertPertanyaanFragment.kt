/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.tools

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tanyapr.R
import com.example.tanyapr.databinding.InsertPertanyaanFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.model.Request
import com.example.tanyapr.tools.*
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.siswa.activity.SiswaViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class InsertPertanyaanFragment : Fragment() {

    companion object {
        fun newInstance() = InsertPertanyaanFragment()
    }

    private var dataPelajaran: List<Pelajaran>? = null
    private var idMataPelajaran: String? = null
    private var coinValue: String? = null
    private lateinit var binding: InsertPertanyaanFragmentBinding
    private val viewModel: SiswaViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = InsertPertanyaanFragmentBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, {
            updateView(it)
        })

        viewModel.pelajaranState.observe(viewLifecycleOwner, {
            updateUi(it)
        })

        viewModel.getMataPelajaran()

        binding.btnUpload.setOnClickListener {
            ImagePicker.with(this)
                    .galleryOnly()
                    .start()
        }

        binding.btnTambah.setOnClickListener {
            val username = Helper.getToken(requireContext()).toString()
            val mataPelajaran = binding.actIdMataPelajaran.text.toString()
            val pertanyaan = binding.tiePertanyaan.getTrimmedText()
            val filePath = binding.fileLocation.text.toString().trim()

            if(viewModel.validate(username, mataPelajaran, pertanyaan, filePath)){
                val field = mapOf(
                        "username" to username.createRequestBody(),
                        "id_mata_pelajaran" to idMataPelajaran!!.createRequestBody(),
                        "coin" to coinValue!!.createRequestBody(),
                        "pertanyaan" to pertanyaan.createRequestBody()
                )
                val file = File(filePath).createFileBody()
                viewModel.insertPertanyaan(field, file)
                val field2 = mapOf(
                    "title" to "Pertanyaan Baru!",
                    "message" to "Ada pertanyaan baru nih buat kamu! Yuk buka aplikasi kamu sekarang!",
                    "topic" to "/topics/$idMataPelajaran"
                )
                viewModel.sendNotification(field2)
            }
        }
    }

    private fun updateView(state: State<Request>) {
        when (state) {
            is State.Success -> {
                val currentCoin = Helper.getToken(requireContext(), Helper.TOKEN_COIN_SISWA)
                val username = Helper.getToken(requireContext())
                coinValue?.let { x ->
                    username?.let {
                        viewModel.updateCoin(it, x)
                    }
                    currentCoin?.let {
                        val coinNow = currentCoin.toInt() - x.toInt()
                        context?.setTokenCoin(Helper.TOKEN_COIN_SISWA, coinNow)
                        findNavController().navigateUp()
                    }
                }
            }
            is State.Fail -> {
                state.message?.let { context?.toast(it) }
            }
            is State.Error -> {
                state.message?.let { context?.toast(it) }
            }
            is State.Invalid -> {
                when (state.condition) {
                    1 -> binding.tilMataPelajaran.error = state.message
                    2 -> binding.tilPertanyaan.error = state.message
                    3 -> binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorError)
                }
            }
            is State.Reset -> {
                binding.tilMataPelajaran.error = null
                binding.tilPertanyaan.error = null
                binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.purple_500)
            }
        }
    }

    private fun updateUi(state: State<List<Pelajaran>>) {
        when (state){
            is State.Success -> {
                dataPelajaran = state.data
                dataPelajaran?.let {
                    val matapelajaranAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, it)
                    binding.actIdMataPelajaran.apply {
                        setAdapter(matapelajaranAdapter)
                        setOnItemClickListener { _, _, position, _ ->
                            val value = matapelajaranAdapter.getItem(position) as Pelajaran
                            idMataPelajaran = value.idMataPelajaran
                            coinValue = value.coin
                            binding.actIdMataPelajaran.setText(value.namaMataPelajaran, false)
                        }
                    }
                }
            }
            is State.Fail -> {
                state.message?.let { context?.toast(it) }
            }
            is State.Error -> {
                state.message?.let { context?.toast(it) }
            }
            is State.Invalid -> {

            }
            is State.Reset -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val filePath:String = ImagePicker.getFilePath(data)!!
                binding.fileLocation.visibility = View.VISIBLE
                binding.fileLocation.text = filePath
            }
            ImagePicker.RESULT_ERROR -> Helper.snackBar(binding.root, ImagePicker.getError(data))
            else -> Helper.snackBar(binding.root, "Task Cancelled")
        }
    }

}