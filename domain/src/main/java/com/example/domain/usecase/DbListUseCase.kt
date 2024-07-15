package com.example.domain.usecase

import com.example.domain.model.ProductFromDb
import com.example.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow

class DbListUseCase(private val dbRepository: DbRepository) {

    fun execute(): Flow<List<ProductFromDb>>{
        return dbRepository.getItemList()
    }
}