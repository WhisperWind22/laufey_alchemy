package com.example.alchemy.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class IngredientWithEffects(
    @Embedded val ingredient: Ingredient,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = IngredientEffectCrossRef::class,
            parentColumn = "ingredient_id",
            entityColumn = "effect_id"
        )
    )
    val additionalEffects: List<Effect>
) 