package com.example.data.dbrepositoryimpl

import com.example.data.database.MyDataBase
import com.example.domain.model.ProductFromDb
import com.example.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow

class DbRepositoryImpl(private val db: MyDataBase) : DbRepository { // наследуемся от Дао, через этот класс реализуем методы работы с БД
    override suspend fun upsertItem(item: ProductFromDb) {
        db.ItemDao().upsertItem(item) // здесь мы имеем конкретный экземпляр БД, и в него добавляем элемент, иначе в интерфейсе у нас нет БД
    }

    override fun getItemList(): Flow<List<ProductFromDb>> {
        return db.ItemDao().getItemList()
    }

    override suspend fun delete(item: ProductFromDb) {
        db.ItemDao().delete(item)
    }
}