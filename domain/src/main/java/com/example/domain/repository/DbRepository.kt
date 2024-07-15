package com.example.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.domain.model.ProductFromDb
import kotlinx.coroutines.flow.Flow


@Dao
interface DbRepository {

    @Upsert
    suspend fun upsertItem(item: ProductFromDb)

    @Query("select * from product")
    fun getItemList(): Flow<List<ProductFromDb>>

    @Delete
    suspend fun delete(item: ProductFromDb)

}