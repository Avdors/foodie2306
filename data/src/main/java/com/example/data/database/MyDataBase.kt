package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.model.ProductFromDb
import com.example.domain.repository.DbRepository

@Database(entities = [ProductFromDb::class], version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun ItemDao() : DbRepository // это у нас Dao с описанием методов

    companion object{
        @Volatile
        private var INSTANSE: MyDataBase? = null // здесь обемпечиваем видимость изменения переменной из других потоков

        fun getDataBase(context: Context): MyDataBase{

        return INSTANSE?: synchronized(this){ // здесь обеспечиваем доступ только одному потоку, по ключу, то есть только один поток может выполнить код одонвременно
        val instanse = Room.databaseBuilder(
            context.applicationContext,
            MyDataBase::class.java,
            "DataBase"
        )
            .fallbackToDestructiveMigration()
            .build()
            INSTANSE = instanse
            // ниже возвращаем возвращаем екземпляр объекта в getDatabase
            instanse
        }


        }
    }
}