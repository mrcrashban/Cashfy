package com.example.compose1.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true) var uidTransaction: Int = 0,
    @ColumnInfo(name = "account_id") var accountIid: Int,
    @ColumnInfo(name = "category_id") var categoryId: Int,
    @ColumnInfo(name = "sum") var sum: Double,
    @ColumnInfo(name = "type_of_transaction") var type: String,
    @ColumnInfo(name = "date") var date: java.util.Date,
    @ColumnInfo(name = "comment") var comment: String
)
