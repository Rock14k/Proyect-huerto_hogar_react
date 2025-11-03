package com.huertohogar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huertohogar.data.model.CartDao
import com.huertohogar.data.model.CartItem

// 🔑 CLASE PRINCIPAL DE LA BASE DE DATOS
@Database(
    entities = [CartItem::class], // Lista de todas las entidades
    version = 1, // Versión de la base de datos
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Método abstracto para obtener el Data Access Object (DAO)
    abstract fun cartDao(): CartDao

    // 🗝️ PATRÓN SINGLETON: Asegura que solo exista una instancia.
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "huertohogar_database"
                )
                    .fallbackToDestructiveMigration() // Útil en desarrollo
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}