/*
 * Copyright (c) 2021. Created by Rio Wirawan.
 * Instagram : @oriorasajambu
 * Github : oriorasajambu.github.io
 */

package com.example.tanyapr.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tanyapr.R
import com.example.tanyapr.databinding.ActivityRegisterBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.model.Pelajaran
import com.example.tanyapr.model.Rekening
import com.example.tanyapr.model.Request
import com.example.tanyapr.tools.createFileBody
import com.example.tanyapr.tools.createRequestBody
import com.example.tanyapr.tools.getTrimmedText
import com.example.tanyapr.view.admin.tools.State
import com.example.tanyapr.viewmodel.RegisterViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.messaging.FirebaseMessaging
import java.io.File

class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinner = parent as Spinner
        when (spinner.id){
            binding.roleSpinner.id -> {
                if(parent.getItemAtPosition(position) == "Guru") {
                    binding.layoutGuru.visibility = View.VISIBLE
                    binding.layoutSiswa.visibility = View.GONE
                }
                else if(parent.getItemAtPosition(position) == "Siswa"){
                    binding.layoutGuru.visibility = View.GONE
                    binding.layoutSiswa.visibility = View.VISIBLE
                }
            }
            binding.mataPelajaran.id -> {
                val pelajaran = parent.getItemAtPosition(position) as Pelajaran
                idMataPelajaran = pelajaran.idMataPelajaran
            }
            binding.rekening.id -> {
                val rekening = parent.getItemAtPosition(position) as Rekening
                binding.tieRekening.setText(rekening.noRekening)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private lateinit var idMataPelajaran: String
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
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

    private fun init(){
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Helper.viewModelFactory {
            RegisterViewModel()
        })[RegisterViewModel::class.java]

        var fcm_token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful) fcm_token = it.result.toString()
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.role,
                android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.roleSpinner.adapter = it
        }

        viewModel.fetchData()

        viewModel.getState().observe(this, {
            updateUi(it)
        })

        viewModel.getRegisterStateGuru().observe(this, {
            updateUiFormGuru(it)
        })

        viewModel.getRegisterStateSiswa().observe(this, {
            updateUiFormSiswa(it)
        })

        val rekening = listOf(
                Rekening("BNI", "123456789"),
                Rekening("Mandiri", "222222222"),
                Rekening("BRI", "333333333")
        )
        binding.rekening.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, rekening)

        binding.roleSpinner.onItemSelectedListener = this
        binding.rekening.onItemSelectedListener = this

        binding.btnUpload.setOnClickListener {
            ImagePicker.with(this)
                    .galleryOnly()
                    .start()
        }

        binding.btnRegisterGuru.setOnClickListener {

            val username = binding.tieUsername.getTrimmedText()
            val password = binding.tiePassword.getTrimmedText()
            val confirmPassword = binding.tieConfirmPassword.getTrimmedText()
            val namaGuru = binding.tieNamaLengkap.getTrimmedText()
            val universitas = binding.tieUniversitas.getTrimmedText()
            val semester = binding.tieSemester.getTrimmedText()
            val filePath = binding.fileLocation.text.toString().trim()

            val formData = listOf(username, password, confirmPassword,namaGuru, universitas, semester, filePath, fcm_token)

            if(viewModel.validateFormGuru(formData)){
                val field = mapOf(
                    "username" to username.createRequestBody(),
                    "password" to password.createRequestBody(),
                    "nama" to namaGuru.createRequestBody(),
                    "universitas" to universitas.createRequestBody(),
                    "semester" to semester.createRequestBody(),
                    "id_mata_pelajaran" to idMataPelajaran.createRequestBody(),
                    "token" to fcm_token.createRequestBody()
                )
                val body = File(filePath).createFileBody()
                viewModel.insertGuru(field, body)
            }
        }

        binding.btnRegisterSiswa.setOnClickListener {

            val username = binding.tieUsername.getTrimmedText()
            val password = binding.tiePassword.getTrimmedText()
            val confirmPassword = binding.tieConfirmPassword.getTrimmedText()
            val namaSiswa = binding.tieNamaLengkapSiswa.getTrimmedText()
            val sekolah = binding.tieSekolah.getTrimmedText()
            val kelas = binding.tieKelas.getTrimmedText()
            val email = binding.tieEmail.getTrimmedText()

            val formData = listOf(username, password, confirmPassword, namaSiswa, sekolah, kelas, email, fcm_token)
            if(viewModel.validateFormSiswa(formData)){
                val field = mapOf(
                    "username" to username,
                    "password" to password,
                    "nama" to namaSiswa,
                    "sekolah" to sekolah,
                    "kelas" to kelas,
                    "email" to email,
                    "token" to fcm_token
                )
                viewModel.insertSiswa(field)
            }
        }
    }

    private fun updateUiFormSiswa(registerState: State<Request>) {
        when (registerState) {
            is State.Success -> {
                Intent(this, LoginActivity::class.java).apply {
                    putExtra("username", binding.tieUsername.getTrimmedText())
                    startActivity(this)
                    ActivityCompat.finishAffinity(this@RegisterActivity)
                }
            }
            is State.Fail -> registerState.message?.let { Helper.toast(this, it) }
            is State.Error -> registerState.message?.let { Helper.toast(this, it) }
            is State.Invalid -> {
                registerState.condition?.let {
                    when (it) {
                        0 -> binding.tilUsername.error = "${registerState.message} Tidak Boleh Kosong"
                        1 -> binding.tilPassword.error = "${registerState.message} Tidak Boleh Kosong"
                        2 -> binding.tilConfirmPassword.error = "${registerState.message} Tidak Boleh Kosong"
                        3 -> binding.tilNamaLengkapSiswa.error = "${registerState.message} Tidak Boleh Kosong"
                        4 -> binding.tilSekolah.error = "${registerState.message} Tidak Boleh Kosong"
                        5 -> binding.tilKelas.error = "${registerState.message} Tidak Boleh Kosong"
                        6 -> binding.tilEmail.error = "${registerState.message} Tidak Boleh Kosong"
                        7 -> binding.tilConfirmPassword.error = "${registerState.message} Tidak Sama Dengan Password"
                        8 -> binding.tilEmail.error = "${registerState.message} Tidak Valid"
                    }
                }
            }
            is State.Reset -> {
                binding.tilUsername.error = null
                binding.tilPassword.error = null
                binding.tilConfirmPassword.error = null
                binding.tilNamaLengkapSiswa.error = null
                binding.tilSekolah.error = null
                binding.tilKelas.error = null
                binding.tilEmail.error = null
            }
        }
    }

    private fun updateUiFormGuru(registerState: State<Request>) {
        when (registerState){
            is State.Success -> {
                Intent(this, LoginActivity::class.java).apply {
                    putExtra("username", binding.tieUsername.getTrimmedText())
                    startActivity(this)
                    ActivityCompat.finishAffinity(this@RegisterActivity)
                }
            }
            is State.Fail -> registerState.message?.let { Helper.toast(this, it) }
            is State.Error -> registerState.message?.let { Helper.toast(this, it) }
            is State.Invalid -> {
                registerState.condition?.let {
                    when (it) {
                        0 -> binding.tilUsername.error =
                            "${registerState.message} Tidak Boleh Kosong"
                        1 -> binding.tilPassword.error =
                            "${registerState.message} Tidak Boleh Kosong"
                        2 -> binding.tilConfirmPassword.error =
                            "${registerState.message} Tidak Boleh Kosong"
                        3 -> binding.tilNamaLengkap.error =
                            "${registerState.message} Tidak Boleh Kosong"
                        4 -> binding.tilUniversitas.error =
                            "${registerState.message} Tidak Boleh Kosong"
                        5 -> binding.tilSemester.error =
                            "${registerState.message} Tidak Boleh Kosong"
                        6 -> {
                            Helper.snackBar(binding.btnRegisterGuru, "Upload Bukti Pembayaran Terlebih Dahulu")
                            binding.btnUpload.backgroundTintList =
                                ContextCompat.getColorStateList(this, R.color.colorError)
                        }
                    }
                }
            }
            is State.Reset -> {
                binding.tilUsername.error = null
                binding.tilPassword.error = null
                binding.tilConfirmPassword.error = null
                binding.tilNamaLengkap.error = null
                binding.tilUniversitas.error = null
                binding.tilSemester.error = null
                binding.btnUpload.backgroundTintList = ContextCompat.getColorStateList(this, R.color.purple_500)
            }
        }
    }

    private fun updateUi(state: State<List<Pelajaran>>) {
        when (state) {
            is State.Success -> {
                state.data?.let {
                    binding.mataPelajaran.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it)
                    binding.mataPelajaran.onItemSelectedListener = this
                }
            }
            is State.Fail -> {
                state.message?.let { Helper.snackBar(binding.root, it) }
            }
            is State.Error -> {
                state.message?.let { Helper.snackBar(binding.root, it) }
            }
            is State.Invalid -> {
                state.message?.let { Helper.snackBar(binding.root, it) }
            }
            is State.Reset -> {

            }
        }
    }

    fun onCheckBoxClicked(view: View){
        if (view is CheckBox){
            val checked = view.isChecked
            when (view.id){
                binding.license.id -> binding.btnRegisterGuru.isEnabled = checked
                binding.license2.id -> binding.btnRegisterSiswa.isEnabled = checked
            }
        }
    }
}