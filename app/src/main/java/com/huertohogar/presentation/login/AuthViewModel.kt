package com.huertohogar.presentation.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.huertohogar.data.repository.UserPreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// 🧠 Clase Sellada para manejar los estados de la UI de autenticación
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: String) : AuthUiState()
    object LoggedOut : AuthUiState() // ⬅️ CORRECCIÓN: Definición agregada
    data class Error(val message: String) : AuthUiState()
}

/**
 * ViewModel para manejar la lógica de Login, Registro y Cierre de Sesión.
 */
class AuthViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    // 💡 Estado de sesión que se lee directamente de DataStore
    val isLoggedIn: StateFlow<Boolean> = preferencesRepository.isLoggedIn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )


    // 1. FUNCIÓN LOGIN
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            delay(1500)

            if (email == "test@huerto.cl" && password == "123456") {
                preferencesRepository.saveLoginState(email)
                _uiState.value = AuthUiState.Success(email)
            } else {
                _uiState.value = AuthUiState.Error("Credenciales incorrectas. Intente de nuevo.")
            }
        }
    }

    // 2. FUNCIÓN REGISTRO
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            delay(2000)

            if (name.isNotBlank() && email.contains("@")) {
                preferencesRepository.saveLoginState(email)
                _uiState.value = AuthUiState.Success(email)
            } else {
                _uiState.value = AuthUiState.Error("Fallo al registrar usuario. Verifique los datos.")
            }
        }
    }

    // 3. FUNCIÓN LOGOUT
    fun logout() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            // 🔐 Borrar estado de DataStore
            preferencesRepository.clearLoginState()
            delay(500) // Simular retardo de cierre

            // 🔑 CORRECCIÓN: Asigna el estado LoggedOut usando el prefijo de la clase
            _uiState.value = AuthUiState.LoggedOut
        }
    }

    // Función para resetear el estado de la UI (útil después de un error o navegación)
    fun resetUiState() {
        _uiState.value = AuthUiState.Idle
    }

    // 🔑 FACTORY para instanciar el ViewModel
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {

                // 1. Crear el Repositorio de Preferencias (DataStore)
                val preferencesRepo = UserPreferencesRepository(application.applicationContext)

                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(preferencesRepo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}