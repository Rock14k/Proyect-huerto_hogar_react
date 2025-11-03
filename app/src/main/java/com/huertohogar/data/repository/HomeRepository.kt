package com.huertohogar.data.repository

import com.huertohogar.data.model.CartDao
import com.huertohogar.data.model.CartItem
import com.huertohogar.data.model.Product
import com.huertohogar.data.model.Testimonial
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repositorio de la pantalla Home.
 * Recibe CartDao en el constructor para las operaciones de persistencia (Room).
 */class HomeRepository(
    private val cartDao: CartDao
) {
    // 💡 Simulación de datos actualizada con TODOS tus Drawables
    private val simulatedProducts = listOf(
        // Verduras y Frutas
        Product(id = "tomates-cherry", name = "Tomates cherry", price = "$3.990 el kg", oldPrice = "$5.000", imageUrlName = "product_1", tag = "Frescos"),
        Product(id = "pimientos-tricolores", name = "Pimientos tricolores", price = "$1.690 UN", oldPrice = "$2.000", imageUrlName = "pimiento_tricolor", tag = "Frescas"),
        Product(id = "zanahorias", name = "Zanahorias", price = "$1.190 1KG", oldPrice = "$2.000", imageUrlName = "zanahorias_1024x683", tag = "Frescas"),
        Product(id = "bananas", name = "Bananas", price = "$1.000 KG", oldPrice = "$1.500", imageUrlName = "bananas", tag = "Frescas"),
        Product(id = "espinaca", name = "Espinaca", price = "$900 Atado", oldPrice = "$1.200", imageUrlName = "espinaca", tag = "Frescas"),
        Product(id = "kiwi", name = "Kiwi", price = "$3.500 KG", oldPrice = "$4.000", imageUrlName = "kiwi", tag = "Frescas"),
        Product(id = "naranjas", name = "Naranjas", price = "$1.100 1kg", oldPrice = "$2.000", imageUrlName = "naranjas", tag = "Frescas"),
        Product(id = "peras", name = "Peras", price = "$2.200 KG", oldPrice = "$2.800", imageUrlName = "peras", tag = "Frescas"),
        Product(id = "uvas", name = "Uvas", price = "$4.500 KG", oldPrice = "$5.000", imageUrlName = "uvas", tag = "Frescas"),

        // Lácteos y Procesados
        Product(id = "leche-chocolate", name = "Leche Sabor Chocolate", price = "$1.099", oldPrice = "$1.300", imageUrlName = "chocolate", tag = "Nuevo"),
        Product(id = "leche-natural", name = "Leche Enteral Natural 1 LT", price = "$1.000", oldPrice = "$1.350", imageUrlName = "leche", tag = "Nuevo"),
        Product(id = "yogurt-batido", name = "Yoghurt Batido Colun", price = "$2.600", oldPrice = "$2.999", imageUrlName = "jogurt", tag = "Nuevo"),
        Product(id = "queso-ranco", name = "Queso Ranco Laminado", price = "$7.000", oldPrice = "$7.250", imageUrlName = "queso", tag = "Nuevo"),
        Product(id = "mantequilla", name = "Mantequilla", price = "$2.500", oldPrice = "$2.900", imageUrlName = "mantequilla", tag = "Nuevo"),
        Product(id = "milo", name = "Milo", price = "$3.000", oldPrice = "$3.500", imageUrlName = "milo", tag = "Nuevo")
    )
    private val simulatedTestimonials = listOf(
        Testimonial(quote = "Mi pareja me metió en el rollo de los productos orgánicos...", name = "Sara", profession = "Arquitecta", imageUrlName = "testimonial_1"),
        Testimonial(quote = "A veces compro productos orgánicos solo porque la calidad del procesamiento es mejor...", name = "Luis", profession = "Electricista", imageUrlName = "testimonial_2"),
        // ... (otros testimonios)
    )

    // 🌐 Simulación de conexión a Internet
    fun getProducts(): Flow<List<Product>> = flow {
        delay(1500)
        emit(simulatedProducts)
    }

    fun getTestimonials(): List<Testimonial> = simulatedTestimonials


    // 🔐 MÉTODOS DE PERSISTENCIA REAL (ROOM)

    /**
     * 1. Obtiene los ítems del carrito desde la base de datos local (Room).
     */
    fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }

    /**
     * 2. Agrega o actualiza un ítem en el carrito.
     */
    suspend fun insertOrUpdateCartItem(item: CartItem) {
        cartDao.insert(item)
    }

    /**
     * 3. Vacía completamente el carrito de compras.
     */
    suspend fun clearCart() {
        cartDao.clearAll()
    }
}