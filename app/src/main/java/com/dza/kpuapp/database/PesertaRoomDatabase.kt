package com.dza.kpuapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Peserta::class], version = 2, exportSchema = false)
abstract class PesertaRoomDatabase : RoomDatabase() {
    abstract fun pesertaDao(): PesertaDao

    companion object {
        @Volatile
        private var INSTANCE: PesertaRoomDatabase? = null

        fun getDatabase(context: Context): PesertaRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PesertaRoomDatabase::class.java,
                    "peserta_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}