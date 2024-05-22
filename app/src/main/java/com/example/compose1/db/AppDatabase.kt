package com.example.compose1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.compose1.db.converters.DateConverter
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.db.entities.RegularTransaction
import com.example.compose1.db.entities.Transaction

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Account::class, Category::class,
        RegularTransaction::class, Transaction::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun TransactionDao(): TransactionDao
    abstract fun AccountDao(): AccountDao
    abstract fun CategoryDao(): CategoryDao

    companion object{
        @Volatile
        var INSTANCE:AppDatabase? = null
        /*val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE category ADD COLUMN image_id INTEGER NOT NULL DEFAULT 0")
            }
        }*/
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
