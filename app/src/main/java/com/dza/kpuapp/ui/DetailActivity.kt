package com.dza.kpuapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dza.kpuapp.database.PesertaDao
import com.dza.kpuapp.database.PesertaRoomDatabase
import com.dza.kpuapp.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var pesertaDao: PesertaDao // DAO untuk Room

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Room Database untuk akses data peserta
        val db = PesertaRoomDatabase.getDatabase(this)
        pesertaDao = db.pesertaDao()

        // Mengambil ID peserta dari Intent
        val pesertaId = intent.getIntExtra("EXTRA_ID", -1)
        if (pesertaId != -1) {
            fetchDetailPeserta(pesertaId) // Ambil data berdasarkan ID peserta
        }

        binding.btnKembali.setOnClickListener {
            finish()
        }
    }

    // Fungsi untuk mengambil data peserta berdasarkan ID
    private fun fetchDetailPeserta(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val peserta = pesertaDao.getPesertaById(id) // Mengambil data peserta dari database
            withContext(Dispatchers.Main) {
                if (peserta != null) {
                    // Menampilkan data peserta di halaman detail
                    with(binding) {
                        editUsername.text = peserta.nama
                        editNIK.text = peserta.nik
                        editGender.text = peserta.gender
                        editAlamat.text = peserta.alamat
                    }
                }
            }
        }
    }
}