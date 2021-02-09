/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.adapter.SiswaAdapter
import com.example.tanyapr.databinding.PageSiswaFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.viewmodel.admin.fragment.UserViewModel

class PageSiswaFragment : Fragment() {

    companion object {
        fun newInstance() = PageSiswaFragment()
    }

    private lateinit var binding : PageSiswaFragmentBinding
    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var siswaAdapter: SiswaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PageSiswaFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerViewSiswa()

        viewModel.getState().observe(viewLifecycleOwner, {
            updateUi(it)
        })

    }

    private fun setupRecyclerViewSiswa() = binding.recyclerview.apply {
        siswaAdapter = SiswaAdapter()
        adapter = siswaAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        viewModel.fetchDataSiswa()
    }

    private fun updateUi(state: UserViewModel.State) {
        when (state){
            is UserViewModel.State.SuccessGuru -> {

            }
            is UserViewModel.State.SuccessSiswa -> {
                state.data.let { siswaAdapter.listSiswa = it }
            }
            is UserViewModel.State.Fail -> {
                Helper.snackBar(binding.root, state.message)
            }
            is UserViewModel.State.Error -> {
                Helper.snackBar(binding.root, state.message)
            }
        }
    }

}