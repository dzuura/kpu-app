package com.dza.kpuapp.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dza.kpuapp.database.Peserta
import com.dza.kpuapp.database.PesertaRoomDatabase
import com.dza.kpuapp.databinding.ItemPesertaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PesertaAdapter(
    private val context: Context,
    private val listPeserta: MutableList<Peserta> // Gunakan MutableList agar data dapat dimodifikasi
) : RecyclerView.Adapter<PesertaAdapter.PesertaViewHolder>() {

    private val db = PesertaRoomDatabase.getDatabase(context) // Inisialisasi Room Database
    private val pesertaDao = db.pesertaDao() // Inisialisasi DAO untuk tabel Peserta

    // ViewHolder untuk mengelola elemen tampilan item
    inner class PesertaViewHolder(val binding: ItemPesertaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesertaViewHolder {
        val binding = ItemPesertaBinding.inflate(LayoutInflater.from(context), parent, false)
        return PesertaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PesertaViewHolder, position: Int) {
        val peserta = listPeserta[position] // Ambil data peserta berdasarkan posisi

        // Tampilkan nama peserta pada item
        holder.binding.editUsername.text = peserta.nama

        // Aksi untuk menghapus peserta
        holder.binding.hapusPeserta.setOnClickListener {
            deleteItem(peserta, position) // Hapus data dari database dan list
        }

        // Aksi untuk membuka DetailActivity dengan data peserta
        holder.binding.detailPeserta.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_ID", peserta.id) // Kirim ID peserta
            }
            context.startActivity(intent) // Navigasi ke DetailActivity
        }

        // Aksi untuk membuka EditActivity dengan data peserta
        holder.binding.editPeserta.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java).apply {
                putExtra("EXTRA_ID", peserta.id) // Kirim ID peserta
            }
            context.startActivity(intent) // Navigasi ke EditActivity
        }
    }

    override fun getItemCount(): Int = listPeserta.size // Hitung jumlah item dalam list

    // Fungsi untuk menghapus data peserta
    private fun deleteItem(peserta: Peserta, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            pesertaDao.delete(peserta) // Hapus data peserta dari database
            withContext(Dispatchers.Main) {
                listPeserta.removeAt(position) // Hapus data dari list
                notifyItemRemoved(position) // Perbarui RecyclerView
            }
        }
    }
}