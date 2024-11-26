package com.dza.kpuapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dza.kpuapp.database.Peserta
import com.dza.kpuapp.database.PesertaDao
import com.dza.kpuapp.database.PesertaRoomDatabase
import com.dza.kpuapp.databinding.ActivityEntryBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    private lateinit var executorService: ExecutorService
    private lateinit var getPesertaDao: PesertaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor() // Menyiapkan thread untuk operasi database
        val db = PesertaRoomDatabase.getDatabase(this)
        getPesertaDao = db.pesertaDao() // Inisialisasi DAO

        with(binding) {
            btnSimpan.setOnClickListener(View.OnClickListener {
                val username = binding.editUsername.text.toString()
                val nik = binding.editNIK.text.toString()
                val alamat = binding.editAlamat.text.toString()
                val selectedId = binding.genderGroup.checkedRadioButtonId

                // Validasi pilihan gender
                if (selectedId == -1) {
                    Toast.makeText(this@EntryActivity, "Pilih gender terlebih dahulu", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val selectedGender = selectedRadioButton.text.toString()

                // Memasukkan data peserta baru ke database
                insert(
                    Peserta(
                        nama = username,
                        nik = nik,
                        gender = selectedGender,
                        alamat = alamat
                    )
                )
                startActivity(Intent(this@EntryActivity, MainActivity::class.java)) // Navigasi ke MainActivity
                setEmptyField() // Mengosongkan field input setelah data disimpan
            })
        }
    }

    // Fungsi untuk menyimpan data peserta ke database
    private fun insert(peserta: Peserta) {
        executorService.execute { getPesertaDao.insert(peserta) }
    }

    // Mengosongkan field input setelah data dimasukkan
    private fun setEmptyField() {
        with(binding) {
            editUsername.text.clear()
            editNIK.text.clear()
            editAlamat.text.clear()
            genderGroup.clearCheck()
        }
    }
}