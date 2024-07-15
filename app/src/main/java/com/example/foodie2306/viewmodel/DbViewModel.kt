package com.example.foodie2306.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.MyDataBase
import com.example.domain.model.ProductFromDb
import com.example.domain.usecase.DbListUseCase
import com.example.domain.usecase.DeleteUseCase
import com.example.domain.usecase.UpsertItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


// в нем мы даем возможность из UI через UseCase запускать методы работы с БД
class DbViewModel(
    private val upsertItemUseCase: UpsertItemUseCase,
    private val dbListUseCase: DbListUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val dataBase: MyDataBase
) : ViewModel() {
    val list = this.dbListUseCase.execute()
        .stateIn(  //этот оператор сохраняет поток в памяти, обновляя его при каждом новом результате запроса к источнику данных.
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), //указывает на то, что данные будут храниться в памяти в течение 5 секунд после последнего обновления из источника. Если за это время не произойдёт новое обновление, данные будут удалены из памяти.
            emptyList() //определяет начальное значение списка
        )
    fun clearDb(){
    viewModelScope.launch(Dispatchers.IO){
        dataBase.clearAllTables()
    }
    }

    fun upsert( // здесь добавляем новые данные, здесь можно наполнить БД тестовыми к примеру
        count: Int,
        id: Int,
        price: Int,
        oldPrice: Int?,
        name: String,
        image: String
    ) {
        viewModelScope.launch {
            val item = ProductFromDb(
                count = count,
                id = id,
                name = name,
                price = price,
                oldPrice = oldPrice,
                image = image
            )
            upsertItemUseCase.execute(item)
        }
    }

    fun plusCount(item: ProductFromDb) { // вот тут пока не понял Count зачем
        viewModelScope.launch {
            val updateItem = ProductFromDb(
                count = item.count + 1,
                id = item.id,
                name = item.name,
                price = item.price,
                oldPrice = item.oldPrice,
                image = item.image
            )
            upsertItemUseCase.execute(updateItem)
        }
    }

    fun minusCount(item: ProductFromDb) {
        viewModelScope.launch {
            val updateItem = ProductFromDb(
                count = item.count - 1,
                id = item.id,
                name = item.name,
                price = item.price,
                oldPrice = item.oldPrice,
                image = item.image
            )
            upsertItemUseCase.execute(updateItem)
            if (item.count == 1) {
                deleteUseCase.execute(item)
            }
        }
    }
}