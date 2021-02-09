/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.guru.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tanyapr.databinding.ExchangeFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Request
import com.example.tanyapr.tools.getTrimmedText
import com.example.tanyapr.tools.setTokenCoin
import com.example.tanyapr.tools.toast
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.guru.fragment.ExchangeViewModel

class ExchangeFragment : Fragment() {

    companion object {
        fun newInstance() = ExchangeFragment()
    }

    private lateinit var binding: ExchangeFragmentBinding
    private lateinit var viewModel: ExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExchangeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ExchangeViewModel::class.java]

        viewModel.state.observe(viewLifecycleOwner, {
            updateView(it)
        })

        val coin = Helper.getToken(requireContext(), Helper.TOKEN_COIN_GURU)

        coin?.let {
            binding.tvCoin.text = it
            binding.tieCoin.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    binding.tilCoin.error = null
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString().isNotEmpty()){
                        if(s.toString().toInt() > it.toInt()) binding.tilCoin.error = "Coin Tidak Cukup"
                        else binding.tilCoin.error = null
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }

        binding.btnRedeem.setOnClickListener {
            val username = Helper.getToken(requireContext())
            val coinTarget = binding.tieCoin.getTrimmedText()
            val rekening = binding.tieRekening.getTrimmedText()
            if(viewModel.validate(coinTarget, coin!!, coinTarget, rekening )){
                val field = mapOf(
                    "username" to username!!,
                    "coin" to coinTarget
                )
                viewModel.redeem(field)
            }

        }

    }

    private fun updateView(state: State<Request>) {
        when (state) {
            is State.Success -> {
                val status = state.data?.status
                status?.let {
                    if (it == 1){
                        val username = Helper.getToken(requireContext())
                        val currentCoin = Helper.getToken(requireContext(), Helper.TOKEN_COIN_GURU)
                        val coinTarget = binding.tieCoin.getTrimmedText()
                        val coinNow = currentCoin!!.toInt() - coinTarget.toInt()
                        requireContext().setTokenCoin(Helper.TOKEN_COIN_GURU, coinNow)
                        val field = mapOf(
                            "title" to "Berhasil Redeem Coin",
                            "message" to "Berhasil redeem $coinTarget coin cek rekening kamu!\nYuk jawab pertanyaan siswa lagi agar bisa redeem lagi!",
                            "username" to username!!
                        )
                        viewModel.sendNotification(field)
                        findNavController().navigate(ExchangeFragmentDirections.actionExchangeFragmentToHomeGuru())
                    }
                    else requireContext().toast("Gagal Redeem")
                }
            }
            is State.Fail -> {
                state.message?.let { requireContext().toast(it) }
            }
            is State.Error -> {
                state.message?.let { requireContext().toast(it) }
            }
            is State.Invalid -> {
                when (state.condition){
                    1 -> binding.tilCoin.error = state.message
                    2 -> binding.tilRekening.error = state.message
                    3 -> binding.tilCoin.error = state.message
                }
            }
            is State.Reset -> {
                binding.apply {
                    tilCoin.error = null
                    tilRekening.error = null
                }
            }
        }
    }

}