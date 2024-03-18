package com.example.compose1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.compose1.db.converters.DateConverter
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.db.entities.RegularTransaction
import com.example.compose1.db.entities.Transaction

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Account::class, Category::class,
        RegularTransaction::class, Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun TransactionDao(): TransactionDao
    abstract fun AccountDao(): AccountDao
    abstract fun CategoryDao(): CategoryDao

    companion object{
        @Volatile
        var INSTANCE:AppDatabase? = null
        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
