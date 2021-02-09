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
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tanyapr.R
import com.example.tanyapr.adapter.PertanyaanAdapter
import com.example.tanyapr.databinding.PertanyaanFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pertanyaan
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.siswa.fragment.PertanyaanViewModel

class PertanyaanFragment : Fragment(), PertanyaanAdapter.OnClickedListener {

    companion object {
        fun newInstance() = PertanyaanFragment()
    }

    private lateinit var binding: PertanyaanFragmentBinding
    private lateinit var viewModel: PertanyaanViewModel
    private lateinit var pertanyaanAdapter: PertanyaanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PertanyaanFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PertanyaanViewModel::class.java)

        setUpRecyclerView()

        viewModel.pertanyaanState.observe(viewLifecycleOwner, {
            updateUi(it)
        })
    }

    private fun updateUi(state: State<List<Pertanyaan>>) {
        when (state) {
            is State.Success -> {
                state.data?.let { pertanyaanAdapter.listPertanyaan = it }
            }
            is State.Fail -> {
                state.message?.let { Helper.snackBar(binding.root, it) }
            }
            is State.Error -> {
                state.message?.let { Helper.snackBar(binding.root, it) }
            }
            is State.Invalid -> {

            }
            is State.Reset -> {

            }
        }
    }

    private fun setUpRecyclerView() = binding.recyclerview.apply {
        pertanyaanAdapter = PertanyaanAdapter()
        adapter = pertanyaanAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        val username = Helper.getToken(requireContext())
        username?.let { viewModel.getPertanyaan(it) }
        pertanyaanAdapter.setOnClickedListener(this)
    }

    override fun onClicked(pertanyaan: Pertanyaan) {
        val bundle = bundleOf("id_soal" to pertanyaan.idSoal, "coin" to pertanyaan.coin, "id_solusi" to pertanyaan.id_solusi)
        findNavController().navigate(R.id.jawabanFragment , bundle)
    }
}