package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.repository.ApiRepository

class ProductListUseCase(private val apiRepository: ApiRepository) {
    suspend fun execute(): List<Product>{
    return  apiRepository.getProducts()
    }
}