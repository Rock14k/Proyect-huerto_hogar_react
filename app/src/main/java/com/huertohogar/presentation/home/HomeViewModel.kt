package com.huertohogar.presentation.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.huertohogar.data.db.AppDatabase
import com.huertohogar.data.model.CartItem
import com.huertohogar.data.model.Product
import com.huertohogar.data.model.Testimonial
import com.huertohogar.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 🧠 Definición de HomeUiState (Necesario para resolver errores en HomeScreen)
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(
        val products: List<Product>,
        val testimonials: List<Testimonial>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}


class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    // ESTADOS
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        // Observa la base de datos local
        viewModelScope.launch {
            repository.getCartItems().collect { items ->
                _cartItems.value = items
            }
        }
        // Carga de datos de la red/repositorio
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {
                repository.getProducts().collect { products ->
                    val testimonials = repository.getTestimonials()
                    _uiState.value = HomeUiState.Success(products, testimonials)
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Fallo la conexión: ${e.message}")
            }
        }
    }

    fun addToCart(product: CartItem) = viewModelScope.launch {
        repository.insertOrUpdateCartItem(product)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }


    // 🔑 FACTORY para instanciar el ViewModel con dependencias
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {

                // 1. Obtener el DAO a través del singleton de la base de datos
                val database = AppDatabase.getDatabase(application)
                val cartDao = database.cartDao()

                // 2. Crear el Repositorio con el DAO
                val repository = HomeRepository(cartDao)

                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}