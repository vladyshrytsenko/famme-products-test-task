package com.example.service

import com.example.model.Product
import com.example.model.ProductDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.ZoneOffset

@Service
class ProductService(private val jdbcClient: JdbcClient, private val restTemplate: RestTemplate) {

    private val productUrl = "https://famme.no/products.json"

    fun fetchAndSaveProducts() {
        val response = restTemplate.getForObject(productUrl, Map::class.java)
        val productsJson = response?.get("products") as? List<*>

        val objectMapper = ObjectMapper().apply {
            registerModule(JavaTimeModule())
        }

        val products = productsJson?.map {
            val productDto = objectMapper.convertValue(it, ProductDto::class.java)
            Product(
                title = productDto.title,
                handle = productDto.handle,
                vendor = productDto.vendor,
                product_type = productDto.productType,
                created_at = productDto.createdAt,
            )
        } ?: emptyList()

        products.takeLast(10).reversed().forEach { product ->
            this.insertProduct(product)
        }
    }

    fun insertProduct(product: Product) {
        jdbcClient.sql(
            "INSERT INTO products (title, handle, vendor, product_type, created_at) " +
                    "VALUES (?, ?, ?, ?, ?)"
        )
            .param(product.title)
            .param(product.handle)
            .param(product.vendor)
            .param(product.product_type)
            .param(product.created_at)
            .update()
    }

    fun getProducts(): List<Product> {
        return jdbcClient.sql("SELECT id, title, handle, vendor, product_type, created_at FROM products")
            .query { rs, _ ->
                Product(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    handle = rs.getString("handle"),
                    vendor = rs.getString("vendor"),
                    product_type = rs.getString("product_type"),
                    created_at = rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC)
                )
            }.list().takeLast(10).reversed()
    }
}
