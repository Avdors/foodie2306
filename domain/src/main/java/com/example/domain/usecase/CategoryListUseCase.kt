package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.repository.ApiRepository

class CategoryListUseCase(private val apiRepository: ApiRepository) {
    suspend fun execute(): List<Category> {
        return apiRepository.getCategories()
    }
}