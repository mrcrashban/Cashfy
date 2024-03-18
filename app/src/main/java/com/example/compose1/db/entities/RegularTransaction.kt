package com.example.compose1.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "regular_transaction")
data class RegularTransaction(
    @PrimaryKey(autoGenerate = true) var uidRegular: Int = 0,
    @ColumnInfo(name = "regular_transaction_name") var name: String,
    @ColumnInfo(name = "frequency_of_operations") var frequency: String,
    @ColumnInfo(name = "regular_transaction_sum") var sum: Double,
    @ColumnInfo(name = "start_date") var date: Date,
    @ColumnInfo(name = "comment") var comment: String
)
