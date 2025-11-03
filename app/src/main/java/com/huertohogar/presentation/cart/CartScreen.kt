package com.huertohogar.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.huertohogar.presentation.Screen
import com.huertohogar.presentation.home.HomeViewModel // Usamos el HomeViewModel para el carrito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController, viewModel: HomeViewModel = viewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val total = cartItems.sumOf { it.price * it.quantity }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mi Carrito (${cartItems.size})") }) }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito Vacío", modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("Tu carrito está vacío.", style = MaterialTheme.typography.titleLarge)
                    TextButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Text("Explorar productos")
                    }
                }
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { item ->
                        // Aquí iría el CartItemRow Composable
                        Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(item.name, modifier = Modifier.weight(1f))
                                Text("x${item.quantity}")
                            }
                        }
                    }
                }

                // Total y Botones (Modal Footer replicado)
                Card(modifier = Modifier.fillMaxWidth(), shape = ShapeDefaults.ExtraSmall) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total:", style = MaterialTheme.typography.headlineSmall)
                            Text("$${"%.2f".format(total)}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(onClick = { viewModel.clearCart() }, colors = ButtonDefaults.outlinedButtonColors()) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Text("Vaciar Carrito")
                            }
                            Button(onClick = { navController.navigate(Screen.Checkout.route) }) {
                                Text("Proceder al Pago")
                            }
                        }
                    }
                }
            }
        }
    }
}