package com.example.compose1.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true) var uidCategory: Int = 0,
    @ColumnInfo(name = "category_name") var categoryName: String,
    @ColumnInfo(name = "image_id") var imageId: Int
)
