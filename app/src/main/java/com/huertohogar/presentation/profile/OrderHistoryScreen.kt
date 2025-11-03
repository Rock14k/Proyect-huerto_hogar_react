package com.huertohogar.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Simula la funcionalidad de order_history.html
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Historial de Compras") }) }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Tus pedidos anteriores aparecerán aquí.", modifier = Modifier.padding(bottom = 24.dp))

            // Placeholder para una tarjeta de historial
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Pedido #00123", style = MaterialTheme.typography.titleMedium)
                    Text("Fecha: 15/10/2025")
                    Text("Total: $15.500", fontWeight = FontWeight.Bold)
                }
            }
            // En una app real: LazyColumn con la lista de pedidos
        }
    }
}