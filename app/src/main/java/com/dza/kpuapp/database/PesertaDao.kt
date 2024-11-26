package com.dza.kpuapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PesertaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(peserta: Peserta)
    @Update
    fun update(peserta: Peserta)
    @Delete
    fun delete(peserta: Peserta)
    @Query("SELECT * FROM peserta ORDER BY id ASC")
    fun getAllPeserta(): LiveData<List<Peserta>>
    @Query("SELECT * FROM peserta WHERE id = :id")
    suspend fun getPesertaById(id: Int): Peserta?
}