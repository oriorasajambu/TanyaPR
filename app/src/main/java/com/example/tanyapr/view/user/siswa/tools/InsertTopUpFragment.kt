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
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tanyapr.R
import com.example.tanyapr.databinding.InsertTopUpFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Coin
import com.example.tanyapr.model.Rekening
import com.example.tanyapr.model.Request
import com.example.tanyapr.tools.createFileBody
import com.example.tanyapr.tools.createRequestBody
import com.example.tanyapr.tools.setTokenCoin
import com.example.tanyapr.tools.toast
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.siswa.activity.SiswaViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class InsertTopUpFragment : Fragment() {

    companion object {
        fun newInstance() = InsertTopUpFragment()
    }

    private lateinit var rekeningValue: String
    private var coinValue: String? = null
    private lateinit var binding: InsertTopUpFragmentBinding
    private val viewModel: SiswaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InsertTopUpFragmentBinding.inflate(layoutInflater)
        return binding.root
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, {
            updateUi(it)
        })

        val rekening = listOf(
            Rekening("BNI", "123456789"),
            Rekening("Mandiri", "222222222"),
            Rekening("BRI", "333333333")
        )

        val coin = listOf(
            Coin("5000", "Rp. 5.000"),
            Coin("25000", "Rp. 25.000"),
            Coin("50000", "Rp. 50.000"),
            Coin("75000", "Rp. 75.000"),
            Coin("100000", "Rp. 100.000")
        )

        val actAdapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, rekening)
        binding.actNoRek.apply {
            setAdapter(actAdapter1)
            setOnItemClickListener { _, _, position, _ ->
                val value = actAdapter1.getItem(position) as Rekening
                rekeningValue = value.rekening
                binding.actNoRek.setText(value.noRekening, false)
            }
        }
        val actAdapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, coin)
        binding.actHarga.apply {
            setAdapter(actAdapter2)
            setOnItemClickListener { _, _, position, _ ->
                val value = actAdapter2.getItem(position) as Coin
                coinValue = value.coin
                binding.actHarga.setText(value.harga, false)
            }
        }

        binding.btnUpload.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .start()
        }

        binding.cbAgreement.setOnClickListener {
            if(it is CheckBox) binding.btnTopUp.isEnabled = it.isChecked
        }

        binding.btnTopUp.setOnClickListener {
            val harga = binding.actHarga.text.toString().trim()
            val noRek = binding.actNoRek.text.toString().trim()
            val filePath = binding.fileLocation.text.toString().trim()
            if(viewModel.validate(harga, noRek, filePath)){
                val username = Helper.getToken(requireContext())
                val role = Helper.getToken(requireContext(), Helper.TOKEN_ROLE)
                val field = mapOf(
                        "username" to username!!.createRequestBody(),
                        "role" to role!!.createRequestBody(),
                        "idr" to harga.createRequestBody(),
                        "coin" to coinValue!!.createRequestBody(),
                        "no_rekening" to noRek.createRequestBody(),
                        "rekening" to rekeningValue.createRequestBody()
                )
                val file = File(filePath).createFileBody()
                viewModel.insertTopUp(field, file)
            }
        }
    }

    private fun updateUi(state: State<Request>){
        when (state) {
            is State.Success -> {
                val currentCoin = Helper.getToken(requireContext(), Helper.TOKEN_COIN_SISWA)
                currentCoin?.let {
                    coinValue?.let { x ->
                        val now = x.toInt() + it.toInt()
                        context?.setTokenCoin(Helper.TOKEN_COIN_SISWA, now)
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
                    1 -> binding.tilCoin.error = state.message
                    2 -> binding.tilNoRek.error = state.message
                    3 -> binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorError)
                }
            }
            is State.Reset -> {
                binding.tilCoin.error = null
                binding.tilNoRek.error = null
                binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.purple_500)
            }
        }
    }

}