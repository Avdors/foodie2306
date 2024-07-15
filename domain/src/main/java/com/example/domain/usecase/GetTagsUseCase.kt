package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.repository.ApiRepository



class GetTagsUseCase(private val apiRepository: ApiRepository) {
    suspend fun execute(): List<Category> {
    return apiRepository.getTags()
    }
}