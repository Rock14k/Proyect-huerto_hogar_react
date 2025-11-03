package com.huertohogar.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 💾 Inicializa DataStore a nivel de aplicación (Singleton implícito)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(context: Context) {

    private val dataStore = context.dataStore

    // 🔑 Claves para almacenar el estado de la sesión
    private object PreferencesKeys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }

    // 1. OBTENER EL ESTADO DE LOGUEO
    // Devuelve un Flow<Boolean> observado por el ViewModel
    val isLoggedIn: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }

    // 2. OBTENER EL CORREO DEL USUARIO ACTUAL
    val userEmail: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL]
        }

    // 3. GUARDAR EL ESTADO DE LOGIN Y EL CORREO
    suspend fun saveLoginState(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
            preferences[PreferencesKeys.USER_EMAIL] = email
        }
    }

    // 4. BORRAR EL ESTADO DE SESIÓN (Logout)
    suspend fun clearLoginState() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = false
            preferences.remove(PreferencesKeys.USER_EMAIL)
        }
    }
}