package com.example.request.model
data class JSendResponse<T>(
    val status: String,
    val data: T? = null,
    val message: String? = null
)
