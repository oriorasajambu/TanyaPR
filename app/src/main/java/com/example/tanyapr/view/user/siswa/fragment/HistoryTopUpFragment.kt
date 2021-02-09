/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.siswa.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.adapter.TopUpAdapter
import com.example.tanyapr.databinding.HistoryTopUpFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.TopUp
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.siswa.fragment.HistoryTopUpViewModel

class HistoryTopUpFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryTopUpFragment()
    }

    private lateinit var topUpAdapter: TopUpAdapter
    private lateinit var binding: HistoryTopUpFragmentBinding
    private lateinit var viewModel: HistoryTopUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryTopUpFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryTopUpViewModel::class.java]

        setUpRecyclerView()

        viewModel.state.observe(viewLifecycleOwner, {
            updateUi(it)
        })

    }

    private fun setUpRecyclerView() = binding.recyclerview.apply {
        topUpAdapter = TopUpAdapter()
        adapter = topUpAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        val username = Helper.getToken(requireContext())
        username?.let { viewModel.getTopUp(it) }
    }

    private fun updateUi(state: State<List<TopUp>>) {
        when(state){
            is State.Success -> {
                state.data?.let{ topUpAdapter.listTopUp = it }
            }
            is State.Fail -> {
                state.message?.let { Helper.toast(requireContext(), it) }
            }
            is State.Error -> {
                state.message?.let { Helper.toast(requireContext(), it) }
            }
            is State.Invalid -> {

            }
            is State.Reset -> {

            }
        }
    }

}