package com.dza.kpuapp.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peserta")
data class Peserta (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int=0,
    @ColumnInfo(name = "nama")
    val nama: String,
    @ColumnInfo(name = "nik")
    val nik: String,
    @ColumnInfo(name = "alamat")
    val alamat: String,
    @ColumnInfo(name = "gender")
    val gender: String
)