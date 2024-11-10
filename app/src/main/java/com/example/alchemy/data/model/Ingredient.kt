package com.example.alchemy.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "material_equivalent")
    val materialEquivalent: String,

    @ColumnInfo(name = "main_effect")
    val mainEffect: String
) 