package com.huertohogar.presentation.about

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Simula la funcionalidad de about.html
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Sobre Nosotros") }) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxWidth()) {
            Text("De la tierra a tu mesa", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(Modifier.height(16.dp))

            // Replicando el contenido de texto del About (index.html)
            Text("Somos una tienda online dedicada a llevar la frescura y calidad de los productos del campo directamente a la puerta de nuestros clientes en Chile. Con más de 6 años de experiencia, operamos en más de 9 puntos a lo largo del país...", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(24.dp))
            Text("Nuestra Misión y Visión", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold))
            Text("Nos comprometemos a fomentar una conexión más cercana entre los consumidores y los agricultores locales, apoyando prácticas agrícolas sostenibles promoviendo una alimentación saludable...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}