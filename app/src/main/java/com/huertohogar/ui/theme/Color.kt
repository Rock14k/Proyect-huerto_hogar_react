// Archivo: Color.kt
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.lightColorScheme

val HuertoPrimary = Color(0xFF3CB815) // Verde (#3CB815)
val HuertoSecondary = Color(0xFFF65005) // Naranja (#F65005)
val HuertoLight = Color(0xFFF7F8FC)
val HuertoDark = Color(0xFF111111)

// Definición de Esquemas de Colores MD3
val LightColorScheme = lightColorScheme(
    primary = HuertoPrimary,
    onPrimary = Color.White,
    secondary = HuertoSecondary,
    onSecondary = Color.White,
    background = HuertoLight,
    surface = Color.White,
    onSurface = HuertoDark
)

// DarkColorScheme (para el modo oscuro)
val DarkColorScheme = darkColorScheme(
    primary = HuertoPrimary,
    onPrimary = Color.Black,
    secondary = HuertoSecondary,
    onSecondary = Color.Black,
    background = HuertoDark,
    surface = Color(0xFF222222),
    onSurface = HuertoLight
)