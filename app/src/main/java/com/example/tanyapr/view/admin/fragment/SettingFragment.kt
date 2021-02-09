/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.tanyapr.databinding.SettingFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Request
import com.example.tanyapr.tools.getTrimmedText
import com.example.tanyapr.view.LoginActivity
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.admin.fragment.SettingViewModel

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var binding: SettingFragmentBinding
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingViewModel::class.java]

        val user = Helper.getToken(requireContext())
        binding.tieUsername.setText(user)

        binding.btnChangePassword.setOnClickListener {
            binding.tilUsername.error = null
            binding.tilNewUsername.error = null
            binding.tilPassword.error = null
            binding.tilNewPassword.error = null
            binding.tilConfirmPassword.error = null
            val username = binding.tieUsername.getTrimmedText()
            val password = binding.tiePassword.getTrimmedText()
            val newUsername = binding.tieNewUsername.getTrimmedText()
            val newPassord = binding.tieNewPassword.getTrimmedText()
            val confirmPassword = binding.tieConfirmPassword.getTrimmedText()
            val field = mapOf(
                    "username" to username,
                    "password" to password,
                    "new_username" to newUsername,
                    "new_password" to newPassord
            )
            if(viewModel.validate(field, confirmPassword)) viewModel.updateAdmin(field)
        }

        viewModel.getState().observe(viewLifecycleOwner, {
            updateUi(it)
        })
    }

    private fun updateUi(state: State<Request>) {
        when(state){
            is State.Success -> {
                val status = state.data?.status
                status?.let {
                    when (it) {
                        0 -> Helper.snackBar(binding.root, "Username / Password Tidak Di Temukan")
                        1 -> Intent(requireActivity(), LoginActivity::class.java).apply {
                            startActivity(this)
                            ActivityCompat.finishAffinity(requireActivity())
                        }.also { Helper.clearToken(requireContext()) }
                        else -> Helper.snackBar(binding.root, "Username / Password Tidak Di Temukan")
                    }
                }
            }
            is State.Fail -> {

            }
            is State.Error -> {

            }
            is State.Invalid -> {
                if(state.condition == 1) binding.tilUsername.error = state.message
                if(state.condition == 2) binding.tilNewUsername.error = state.message
                if(state.condition == 3) binding.tilPassword.error = state.message
                if(state.condition == 4) binding.tilNewPassword.error = state.message
                if(state.condition == 5) binding.tilConfirmPassword.error = state.message
            }
            is State.Reset -> {

            }
        }
    }

}