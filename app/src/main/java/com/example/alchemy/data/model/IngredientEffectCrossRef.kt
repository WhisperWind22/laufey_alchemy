package com.example.alchemy.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ingredient_effect_cross_ref",
    primaryKeys = ["ingredient_id", "effect_id"],
    foreignKeys = [
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredient_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Effect::class,
            parentColumns = ["id"],
            childColumns = ["effect_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IngredientEffectCrossRef(
    @ColumnInfo(name = "ingredient_id") val ingredientId: Int,
    @ColumnInfo(name = "effect_id") val effectId: Int
) 