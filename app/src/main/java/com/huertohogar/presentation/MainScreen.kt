package com.huertohogar.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huertohogar.R
import com.huertohogar.data.model.Product
import com.huertohogar.presentation.home.HomeScreen
import com.huertohogar.presentation.login.LoginScreen
import com.huertohogar.presentation.registro.RegistroScreen
import com.huertohogar.presentation.cart.CartScreen
import com.huertohogar.presentation.checkout.CheckoutScreen
import com.huertohogar.presentation.about.AboutScreen
import com.huertohogar.presentation.blog.BlogScreen
import com.huertohogar.presentation.profile.OrderHistoryScreen
import com.huertohogar.presentation.profile.LogoutScreen
import com.huertohogar.presentation.components.Error404Screen

// 1. 🔁 Rutas de Navegación extendidas
sealed class Screen(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector?, val label: String) {
    object Home : Screen("home", Icons.Default.Home, "Inicio")
    object Products : Screen("products", Icons.Default.ShoppingCart, "Productos")
    object About : Screen("about", Icons.Default.Info, "Nosotros")
    object Blog : Screen("blog", Icons.Default.Description, "Blog")
    object Profile : Screen("profile", Icons.Default.Person, "Perfil")
    // Rutas sin icono en la barra inferior, pero usadas para navegación:
    object Login : Screen("login", null, "Login")
    object Register : Screen("registro", null, "Registro")
    object Cart : Screen("cart", null, "Carrito")
    object Checkout : Screen("checkout", null, "Pago")
    object OrderHistory : Screen("order_history", null, "Historial")
    object Logout : Screen("logout", null, "Cerrar Sesión")
    object NotFound : Screen("404", null, "No Encontrada")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        AppNavHost(navController, Modifier.padding(innerPadding))
    }
}

// 2. 🧭 AppNavHost modificado para incluir todas las rutas
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Products.route) { Text("Pantalla Productos") }
        composable(Screen.About.route) { AboutScreen(navController) }
        composable(Screen.Blog.route) { BlogScreen(navController) }
        composable(Screen.Profile.route) { Text("Pantalla Perfil (Placeholder)") }

        // Rutas funcionales
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegistroScreen(navController) }
        composable(Screen.Cart.route) { CartScreen(navController) }
        composable(Screen.Checkout.route) { CheckoutScreen(navController) }
        composable(Screen.OrderHistory.route) { OrderHistoryScreen(navController) }
        composable(Screen.Logout.route) { LogoutScreen(navController) }
        composable(Screen.NotFound.route) { Error404Screen(navController) }

        // Manejo de rutas desconocidas
        composable("{*url}") { Error404Screen(navController) }
    }
}

// 3. Barra de Navegación Inferior
@Composable
fun AppBottomBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Products, Screen.Profile) // Solo rutas con iconos
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                // 🔑 CORRECCIÓN DEL ERROR: Usar !! para afirmar que el icono no es nulo
                icon = { Icon(screen.icon!!, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }}
            )
        }
    }
}

// 4. App Bar (Top Bar)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Row {
                Text(
                    text = "Huerto",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Hogar",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            // Icono de Login/Perfil
            IconButton(onClick = { navController.navigate(Screen.Login.route) }) {
                Icon(Icons.Default.Person, contentDescription = "Perfil/Login", tint = Color.Black)
            }
            // Icono de Carrito con contador (Badge)
            IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                BadgedBox(badge = { Badge(containerColor = MaterialTheme.colorScheme.secondary) { Text("3") } }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Color.Black)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
    )
}

// 5. Tarjeta de Producto Reutilizable (ProductCard)
@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.padding(4.dp).fillMaxWidth().clickable { /* Navegar a detalle */ },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp).background(Color.Gray.copy(alpha = 0.1f))
            ) {
                // Placeholder para la imagen real (debe usar R.drawable.tu_imagen_id)
                // Image(painter = painterResource(id = R.drawable.placeholder), contentDescription = null)

                // Etiqueta "Frescos"
                Text(
                    text = product.tag,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(text = product.price, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = product.oldPrice,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough)
                    )
                }
                Button(
                    onClick = { /* Añadir al carrito */ },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text("Añadir al carrito")
                }
            }
        }
    }
}