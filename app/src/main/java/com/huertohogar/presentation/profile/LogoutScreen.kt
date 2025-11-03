package com.huertohogar.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.huertohogar.presentation.Screen
import com.huertohogar.presentation.login.AuthViewModel
import kotlinx.coroutines.delay

// Simula la funcionalidad de Cierre_sesion.html
@Composable
fun LogoutScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {

    // 💡 Ejecuta la lógica de cierre de sesión al cargar la pantalla
    LaunchedEffect(Unit) {
        // viewModel.logout() // Llama a la función real de logout
        delay(1000) // Simula el tiempo de procesamiento
        navController.navigate(Screen.Home.route) {
            // Limpia la pila de navegación para que el usuario no pueda volver
            popUpTo(navController.graph.id) { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text("Cerrando Sesión...", style = MaterialTheme.typography.headlineSmall)
        }
    }
}