/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.adapter.TopUpAdapter
import com.example.tanyapr.databinding.TopUpFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.TopUp
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.admin.fragment.TopUpViewModel

class TopUpFragment : Fragment() {

    companion object {
        fun newInstance() = TopUpFragment()
    }

    private lateinit var topUpAdapter: TopUpAdapter
    private lateinit var binding: TopUpFragmentBinding
    private lateinit var viewModel: TopUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TopUpFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, Helper.viewModelFactory {
            TopUpViewModel()
        })[TopUpViewModel::class.java]

        setupRecyclerView()

        viewModel.getState().observe(viewLifecycleOwner, {
            updateUi(it)
        })

    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        topUpAdapter = TopUpAdapter()
        adapter = topUpAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        viewModel.getTopUp()
    }

    private fun updateUi(state: State<List<TopUp>>) {
        when(state){
            is State.Success -> state.data?.let { topUpAdapter.listTopUp = it }
            is State.Fail -> {

            }
            is State.Error -> {

            }
            is State.Invalid -> {

            }
            is State.Reset -> {

            }
        }
    }

}