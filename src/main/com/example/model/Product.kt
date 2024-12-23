package com.example.model

import java.time.OffsetDateTime

data class Product(
    val id: Long = 0,
    val title: String,
    val handle: String,
    val vendor: String,
    val product_type: String,
    val created_at: OffsetDateTime
)
