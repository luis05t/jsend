package com.example.request.model

// Clase genérica para la respuesta JSend
data class JSendResponse<T>(
    val status: String,        // "success", "fail" o "error"
    val data: T? = null,       // Datos devueltos (pueden ser null si no hay)
    val message: String? = null // Mensaje opcional que describe el resultado
)
