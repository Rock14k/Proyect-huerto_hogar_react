package com.huertohogar.data.model
data class Product(
    val id: String,
    val name: String,
    val price: String,
    val oldPrice: String,
    val imageUrlName: String,
    val tag: String

)

data class Testimonial(
    val quote: String,
    val name: String,
    val profession: String,
    val imageUrlName: String

)