package com.huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel // ⬅️ Nuevo import (aunque ya podría estar)
import com.huertohogar.presentation.MainScreen
import com.huertohogar.presentation.home.HomeViewModel // ⬅️ Nuevo import para la Factory
import com.huertohogar.ui.theme.HuertohogarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HuertohogarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //  MODIFICACIÓN CLAVE: Inicialización de la Factory de Room/ViewModel
                    // HomeViewModel.Factory se encarga de crear la DB y el Repository
                    val homeViewModel: HomeViewModel = viewModel(
                        factory = HomeViewModel.Factory(application)
                    )

                    // Pasamos el ViewModel al Composable principal si es necesario,
                    // o lo dejamos que lo resuelva el NavHost.
                    MainScreen()

                    // NOTA: La inyección más limpia es resolver el ViewModel directamente
                    // en HomeScreen.kt, pero inicializar la Factory aquí asegura que
                    // la aplicación sabe cómo crearlo. La forma más simple es:

                    // MainScreen(homeViewModel = homeViewModel)
                    // Y luego usar ese ViewModel en el NavHost, pero la solución
                    // estándar de Compose es la que ya implementamos en HomeScreen.kt
                    // (usando LocalContext.current.applicationContext as Application).

                    // Mantenemos la llamada a MainScreen() simple.
                }
            }
        }
    }
}