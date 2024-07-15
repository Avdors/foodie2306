package com.example.domain.repository

import com.example.domain.model.Category
import com.example.domain.model.Product

interface ApiRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getCategories(): List<Category>
    suspend fun getTags(): List<Category>
}