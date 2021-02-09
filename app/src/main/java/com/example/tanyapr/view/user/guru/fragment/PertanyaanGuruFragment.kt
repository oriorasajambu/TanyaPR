/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.guru.fragment

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
import com.example.tanyapr.adapter.PertanyaanGuruAdapter
import com.example.tanyapr.databinding.PertanyaanGuruFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pertanyaan
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.guru.fragment.PertanyaanGuruViewModel

class PertanyaanGuruFragment : Fragment(), PertanyaanGuruAdapter.OnClickListener {

    companion object {
        fun newInstance() = PertanyaanGuruFragment()
    }

    private lateinit var pertanyaanGuruAdapter: PertanyaanGuruAdapter
    private lateinit var binding: PertanyaanGuruFragmentBinding
    private lateinit var viewModel: PertanyaanGuruViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = PertanyaanGuruFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PertanyaanGuruViewModel::class.java]

        setupRecyclerView()

        viewModel.pertanyaanState.observe(viewLifecycleOwner, {
            updateUi(it)
        })

    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        pertanyaanGuruAdapter = PertanyaanGuruAdapter()
        adapter = pertanyaanGuruAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        val idmatapelajaran = Helper.getToken(requireContext(), Helper.TOKEN_ID_MATA_PELAJARAN)
        idmatapelajaran?.let { viewModel.getPertanyaan(it.toInt()) }
    }

    private fun updateUi(state: State<List<Pertanyaan>>) {
        when (state) {
            is State.Success -> state.data?.let {
                pertanyaanGuruAdapter.listPertanyaan = it
                pertanyaanGuruAdapter.setOnClickedListener(this)
                when (pertanyaanGuruAdapter.itemCount) {
                    0 -> binding.placeHolder.visibility = View.VISIBLE
                    else -> binding.placeHolder.visibility = View.GONE
                }
            }
            is State.Fail -> state.message?.let { Helper.snackBar(binding.root, it) }
            is State.Error -> state.message?.let { Helper.snackBar(binding.root, it) }
            is State.Invalid -> {

            }
            is State.Reset -> {

            }
        }
    }

    override fun onClicked(pertanyaan: Pertanyaan) {
        val bundle = bundleOf("data" to pertanyaan)
        findNavController().navigate(R.id.jawabFragment, bundle)
    }

}