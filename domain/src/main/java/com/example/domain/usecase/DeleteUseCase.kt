package com.example.domain.usecase

import com.example.domain.model.ProductFromDb
import com.example.domain.repository.DbRepository

class DeleteUseCase(private val dbRepository: DbRepository) {
    suspend fun execute(item: ProductFromDb){
        return dbRepository.delete(item)
    }
}