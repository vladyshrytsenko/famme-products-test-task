package com.example.component

import com.example.service.ProductService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledJob(private val productService: ProductService) {

    @Scheduled(initialDelay = 0, fixedRate = Long.MAX_VALUE)
    fun populateDatabase() {
        productService.fetchAndSaveProducts()
    }
}
