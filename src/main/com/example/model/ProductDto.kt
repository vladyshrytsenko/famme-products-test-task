package com.example.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProductDto @JsonCreator constructor(
    @JsonProperty("id") val id: Long,
    @JsonProperty("title") val title: String,
    @JsonProperty("handle") val handle: String,
    @JsonProperty("vendor") val vendor: String,
    @JsonProperty("product_type") val productType: String,
    @JsonProperty("created_at") val createdAt: OffsetDateTime
)
