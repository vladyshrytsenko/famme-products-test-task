package com.example.controller

import com.example.model.Product
import com.example.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/test")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getProducts(): List<Product> = productService.getProducts()

    @GetMapping("/table")
    fun getProductTableFragment(model: Model): String {
        val products = productService.getProducts()

        model.addAttribute("products", products)
        return "fragments/product-table :: product-table"
    }

    @PostMapping("/add-product")
    fun addProduct(
        @RequestBody body: Product
    ): ResponseEntity<Void> {

        productService.insertProduct(body)
        return ResponseEntity.ok().build()
    }
}
