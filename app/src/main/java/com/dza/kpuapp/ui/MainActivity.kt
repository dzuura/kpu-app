package com.dza.kpuapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dza.kpuapp.database.PesertaDao
import com.dza.kpuapp.database.PesertaRoomDatabase
import com.dza.kpuapp.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var getPesertaDao: PesertaDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor() // ExecutorService untuk thread tunggal
        val db = PesertaRoomDatabase.getDatabase(this) // Inisialisasi database
        getPesertaDao = db!!.pesertaDao()!! // Ambil PesertaDao
        prefManager = PrefManager.getInstance(this) // Inisialisasi PrefManager

        with(binding) {
            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false) // Set status logout
                startActivity(Intent(this@MainActivity, LoginActivity::class.java)) // Navigasi ke Login
                finish()
            }

            btnTambaData.setOnClickListener {
                startActivity(Intent(this@MainActivity, EntryActivity::class.java)) // Navigasi ke EntryActivity
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getAllPeserta() // Memuat ulang data peserta
    }

    private fun getAllPeserta() {
        getPesertaDao.getAllPeserta().observe(this) { pesertaList ->
            val adapter = PesertaAdapter(this, pesertaList.toMutableList()) // Atur adapter
            binding.rvDataPeserta.adapter = adapter // Tampilkan data ke RecyclerView
        }
    }
}