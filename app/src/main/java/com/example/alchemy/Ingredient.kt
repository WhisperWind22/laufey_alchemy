// File: Ingredient.kt
package com.example.alchemy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey val id: Int,
    val name: String,
    val positiveEffects: List<String>,
    val negativeEffects: List<String>,
    val imageUrl: String,
    val description: String,
    val value: String,
    val weight: String
)