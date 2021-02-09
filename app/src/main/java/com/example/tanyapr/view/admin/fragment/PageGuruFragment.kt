/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.admin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.adapter.GuruAdapter
import com.example.tanyapr.databinding.PageGuruFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.viewmodel.admin.fragment.UserViewModel

class PageGuruFragment : Fragment() {

    companion object {
        fun newInstance() = PageGuruFragment()
    }

    private lateinit var binding: PageGuruFragmentBinding
    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var guruAdapter: GuruAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PageGuruFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRecyclerViewGuru()

        viewModel.getState().observe(viewLifecycleOwner, {
            updateUi(it)
        })

    }

    private fun setupRecyclerViewGuru() = binding.recyclerview.apply {
        guruAdapter = GuruAdapter()
        adapter = guruAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        viewModel.fetchDataGuru()
    }

    private fun updateUi(state: UserViewModel.State) {
        when (state){
            is UserViewModel.State.SuccessGuru -> {
                state.data.let { guruAdapter.listGuru = it }
            }
            is UserViewModel.State.SuccessSiswa -> {

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