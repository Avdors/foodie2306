package com.example.domain.usecase

import com.example.domain.model.ProductFromDb
import com.example.domain.repository.DbRepository

class UpsertItemUseCase(private val dbRepository: DbRepository) {
    suspend fun execute(item: ProductFromDb){
        dbRepository.upsertItem(item)
    }
}