// File: Ingredient.kt
package com.example.alchemy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey val id: Int,
    val name: String,
    val primaryEffect: String,
    val secondaryEffect: String,
    val tertiaryEffect: String,
    val quaternaryEffect: String,
    val description: String,
    val imageUrl: String
)