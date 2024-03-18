package com.example.compose1.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true) var uidAccount: Int = 0,
    @ColumnInfo(name = "account_name") var accountName: String,
    @ColumnInfo(name = "account_sum") var accountSum: String
)
