/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view.user.guru.fragment

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.example.tanyapr.R
import com.example.tanyapr.databinding.JawabFragmentBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pertanyaan
import com.example.tanyapr.model.Request
import com.example.tanyapr.tools.createFileBody
import com.example.tanyapr.tools.createRequestBody
import com.example.tanyapr.tools.getTrimmedText
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.user.guru.fragment.JawabViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import java.io.File

class JawabFragment : Fragment() {

    companion object {
        fun newInstance() = JawabFragment()
    }

    private lateinit var binding: JawabFragmentBinding
    private lateinit var viewModel: JawabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = JawabFragmentBinding.inflate(inflater)

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            val isScrolled = oldScrollY - scrollY
            if(isScrolled < 0) (v!!.context as Helper.OnScrolledListener).performHide()
            else (v!!.context as Helper.OnScrolledListener).performShow()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[JawabViewModel::class.java]

        val pertanyaan = arguments?.let { bundle ->
            bundle.getSerializable("data") as Pertanyaan
        }



        binding.apply {
            pertanyaan?.let { data ->
                username.text = "Username : ${data.penanya}"
                question.text = "Pertanyaan : ${data.pertanyaan}"
                coin.text = "Coin : ${data.coin}"
                val gambar = data.getImage()
                gambar?.let { pic ->
                    Picasso.get()
                        .load(pic)
                        .error(R.drawable.ic_error_image)
                        .into(picture)
                }
                btnSend.setOnClickListener {
                    val idSoal = data.idSoal.toString()
                    val username = Helper.getToken(requireContext()).toString()
                    val solusi = tieJawaban.getTrimmedText()
                    val filePath = binding.fileLocation.text.toString().trim()

                    if(viewModel.validate(idSoal, username, solusi, filePath)){
                        val field = mapOf(
                                "id_soal" to idSoal.createRequestBody(),
                                "username" to username.createRequestBody(),
                                "solusi" to solusi.createRequestBody()
                        )
                        val file = File(filePath).createFileBody()
                        viewModel.insertJawaban(field, file)
                    }
                }
                viewModel.state.observe(viewLifecycleOwner, {
                    updateUi(it, data)
                })
            }
            btnUpload.setOnClickListener {
                ImagePicker.with(this@JawabFragment)
                    .galleryOnly()
                    .start()
            }
        }
    }

    private fun updateUi(state: State<Request>, data: Pertanyaan) {
        when (state) {
            is State.Success -> {
                val value = state.data
                value?.let {
                    when {
                        it.status == 1 && it.error == null -> {
                            val field = mapOf(
                                    "title" to "Pertanyaan Kamu Dijawab!",
                                    "message" to "Hey ${data.penanya}, Pertanyaan Kamu Sudah Dijawab Loh!\nYuk Lihat Jawabannya Sekarang!",
                                    "username" to data.penanya
                            )
                            viewModel.sendNotification(field)
                        }
                    }
                }
            }
            is State.Fail -> {
                state.message?.let { Helper.toast(requireContext(), it) }
            }
            is State.Error -> {
                state.message?.let { Helper.toast(requireContext(), it) }
            }
            is State.Invalid -> {
                when (state.condition){
                    3 -> binding.tilJawaban.error = state.message
                    4 -> binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(
                            requireContext(), R.color.colorError
                    )
                }
            }
            is State.Reset -> {
                binding.tilJawaban.error = null
                binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(
                        requireContext(), R.color.purple_500
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val filePath:String = ImagePicker.getFilePath(data)!!
                binding.fileLocation.visibility = View.VISIBLE
                binding.fileLocation.text = filePath
            }
            ImagePicker.RESULT_ERROR -> Helper.snackBar(binding.root, ImagePicker.getError(data))
            else -> Helper.snackBar(binding.root, "Task Cancelled")
        }
    }

}