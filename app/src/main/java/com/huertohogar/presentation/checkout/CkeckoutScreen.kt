package com.huertohogar.presentation.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Simula la funcionalidad de checkout.html
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Proceso de Pago") }) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxWidth()) {
            Text("Detalles de Envío y Pago", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            // Sección de Datos de Envío (simulada)
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Región / Ciudad") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(24.dp))

            Text("Resumen del Pedido:", style = MaterialTheme.typography.titleLarge)
            // Aquí se mostraría la lista final del carrito (usando el ViewModel)
            Spacer(Modifier.height(16.dp))

            Button(onClick = { /* Lógica de confirmación de pago */ }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Text("Confirmar Pedido (Pagar)")
            }
        }
    }
}