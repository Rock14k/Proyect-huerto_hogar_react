package com.huertohogar.presentation.home

import android.app.Application // ⬅️ IMPORTACIÓN FALTANTE: Resuelve 'Application'
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // ⬅️ IMPORTACIÓN FALTANTE: Resuelve 'LocalContext'
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.huertohogar.R // Importa la clase R para los recursos
import com.huertohogar.data.model.Product
import com.huertohogar.data.model.Testimonial
import com.huertohogar.presentation.AppBar
import com.huertohogar.presentation.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    // 🔑 CORRECCIÓN CRÍTICA: La inyección de la Factory ahora es correcta
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory(LocalContext.current.applicationContext as Application)
    )
) {
    // 🧠 Observación del estado
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(topBar = { AppBar(navController) }) { paddingValues ->
        when (uiState) {
            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    Text((uiState as HomeUiState.Error).message, color = Color.Red)
                }
            }
            is HomeUiState.Success -> {
                val data = uiState as HomeUiState.Success

                LazyColumn(modifier = Modifier.padding(paddingValues)) {

                    item { CarouselSection() }
                    item { AboutSection() }
                    item { FeatureSection() }
                    item { ProductsHeader() }

                    item {
                        // Lista de productos con LazyVerticalGrid
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .heightIn(min = 1.dp, max = 1500.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = false
                        ) {
                            items(data.products, key = { it.id }) { product ->
                                ProductCard(product)
                            }
                        }
                    }
                    item { TestimonialSection(data.testimonials) }
                }
            }
        }
    }
}

// 💡 IMPLEMENTACIONES PLACEHOLDER (SIN CAMBIOS)

@Composable
fun CarouselSection() {
    val currentImage = remember { mutableStateOf(R.drawable.carousel_1) }

    Crossfade(targetState = currentImage.value, modifier = Modifier.fillMaxWidth().height(200.dp), label = "CarouselCrossfade") { drawableId ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "Carrusel de portada",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Añadido para mejor visualización
            )
            Text(
                "Cultiva salud, compra Orgánico",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

@Composable
fun AboutSection() {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp).padding(16.dp)) {
        Text("De la tierra a tu mesa (Nosotros)", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun FeatureSection() {
    Row(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp)) {
        Text("Nuestras Características", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun ProductsHeader() {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Nuestros Productos", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun TestimonialSection(testimonials: List<Testimonial>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Reseñas de nuestros clientes", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        Spacer(Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(testimonials) { testimonial ->
                Card(modifier = Modifier.width(300.dp).padding(8.dp)) {
                    Text(testimonial.quote, modifier = Modifier.padding(12.dp))
                    Text("- ${testimonial.name}", modifier = Modifier.padding(start = 12.dp, bottom = 8.dp))
                }
            }
        }
    }
}