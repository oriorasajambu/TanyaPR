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
import com.example.tanyapr.adapter.JawabanAdapter
import com.example.tanyapr.databinding.JawabanFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Jawaban
import com.example.tanyapr.model.JawabanX
import com.example.tanyapr.tools.toast
import com.example.tanyapr.view.admin.tools.State
import com.squareup.picasso.Picasso

class JawabanFragment : Fragment(), JawabanAdapter.OnClickedListener, Helper.Refresh {

    companion object {
        fun newInstance() = JawabanFragment()
    }

    private lateinit var jawabanAdapter: JawabanAdapter
    private lateinit var binding: JawabanFragmentBinding
    private lateinit var viewModel: JawabanViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = JawabanFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JawabanViewModel::class.java)

        viewModel.state.observe(viewLifecycleOwner, {
            updateUi(it)
        })

        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        val idSolusi = arguments?.getInt("id_solusi")
        jawabanAdapter = JawabanAdapter(idSolusi)
        adapter = jawabanAdapter
        jawabanAdapter.setOnClickedListener(this@JawabanFragment)
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }.also {
        val idSoal : Int? = arguments?.getInt("id_soal", 0)
        idSoal?.let { viewModel.fetchJawaban(it) }
    }

    override fun onClicked(jawaban: JawabanX) {
        setOnRefreshListener(this)
        val coin = arguments?.getInt("coin", 0)
        val bundle = bundleOf(
            "data" to jawaban,
            "coin" to coin
        )
        findNavController().navigate(R.id.confirmationDialogFragment, bundle)
    }

    private fun updateUi(state: State<Jawaban>) {
        when (state){
            is State.Success -> {
                val data = state.data
                data?.let {
                    Picasso.get().load(it.getImage()).into(binding.ivGambar)
                    binding.tvPertanyaan.text = it.pertanyaan
                    jawabanAdapter.listJawabanX = it.jawaban
                }
            }
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

    override fun onRefresh() {
        findNavController().navigate(ConfirmationDialogFragmentDirections.actionConfirmationDialogFragmentToHome())
    }
}