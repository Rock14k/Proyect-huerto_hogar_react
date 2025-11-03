package com.huertohogar.presentation.blog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Simula la funcionalidad de blog.html
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Blog y Noticias") }) }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            item {
                Text("Últimas Publicaciones del Blog", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))
            }

            // Aquí iría el listado real de posts (simulado)
            items(3) { index ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Alimentos orgánicos: Un mercado en expansión", style = MaterialTheme.typography.titleLarge)
                        Text("25 Ene, 2025 - Revista Nutrición", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { /* Navegar a detalle del post */ }) {
                            Text("Leer más")
                        }
                    }
                }
            }
        }
    }
}