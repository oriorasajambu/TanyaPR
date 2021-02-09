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
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.R
import com.example.tanyapr.adapter.PelajaranAdapter
import com.example.tanyapr.databinding.HomeFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.viewmodel.admin.fragment.HomeViewModel

class HomeFragment : Fragment(), PelajaranAdapter.OnClickedListener, Helper.Refresh {

    private lateinit var pelajaranAdapter: PelajaranAdapter
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)

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
            HomeViewModel()
        })[HomeViewModel::class.java]

        setupRecyclerView()

        viewModel.getHomeState().observe(viewLifecycleOwner, {
            updateUi(it)
        })

    }

    private fun updateUi(state: HomeViewModel.HomeState) {
        when (state) {
            is HomeViewModel.HomeState.Loading -> when (state.isLoading){
                true -> binding.shimmerFrameLayout.apply {
                    visibility = View.VISIBLE
                    startShimmer()
                }
                false -> binding.shimmerFrameLayout.apply {
                    visibility = View.GONE
                    stopShimmer()
                }
            }
            is HomeViewModel.HomeState.Success -> {
                state.data?.let { pelajaranAdapter.listPelajaran = it }
            }
            is HomeViewModel.HomeState.Fail -> {
                Helper.snackBar(binding.root, state.message)
            }
            is HomeViewModel.HomeState.Error -> {

            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        pelajaranAdapter = PelajaranAdapter()
        pelajaranAdapter.setOnClickedListener(this@HomeFragment)
        adapter = pelajaranAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        viewModel.fetchData()
        setOnRefreshListener(this)
    }

    override fun onRefresh() {
        pelajaranAdapter.listPelajaran = listOf()
        viewModel.fetchData()
    }

    override fun onDeleteClicked(data: Pelajaran) {
        val bundle = bundleOf("data" to data)
        findNavController().navigate(R.id.homeDialogFragment, bundle)
    }

    override fun onEditClicked(data: Pelajaran) {
        val bundle = bundleOf("data" to data)
        findNavController().navigate(R.id.homeBottomFragment, bundle)
    }

}