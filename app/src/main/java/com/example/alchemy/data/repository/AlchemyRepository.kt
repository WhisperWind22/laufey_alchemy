package com.example.alchemy.data.repository

import com.example.alchemy.data.dao.IngredientDao
import com.example.alchemy.data.model.Effect
import com.example.alchemy.data.model.Ingredient
import com.example.alchemy.data.model.IngredientEffectCrossRef
import com.example.alchemy.data.model.IngredientWithEffects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlchemyRepository(private val ingredientDao: IngredientDao) {

    fun getAllIngredients() = ingredientDao.getAllIngredients()

    suspend fun findIdealPotion(effect: String): List<IngredientWithEffects> {
        return withContext(Dispatchers.IO) {
            ingredientDao.getIngredientsByEffect(effect)
        }
    }

    suspend fun addIngredient(ingredient: Ingredient) {
        withContext(Dispatchers.IO) {
            ingredientDao.insertIngredient(ingredient)
        }
    }

    suspend fun deleteIngredient(ingredient: Ingredient) {
        withContext(Dispatchers.IO) {
            ingredientDao.deleteIngredient(ingredient)
        }
    }

    suspend fun getIngredientById(id: Int): IngredientWithEffects? {
        return withContext(Dispatchers.IO) {
            ingredientDao.getIngredientById(id)
        }
    }

    suspend fun addIngredientWithEffects(
        ingredient: Ingredient,
        effects: List<Effect>,
        crossRefs: List<IngredientEffectCrossRef>
    ) {
        withContext(Dispatchers.IO) {
            val ingredientId = ingredientDao.insertIngredient(ingredient)

            effects.forEach { effect ->
                val existingEffect = ingredientDao.getEffectByName(effect.name)
                val effectId = existingEffect?.id ?: ingredientDao.insertEffect(effect)

                ingredientDao.insertCrossRef(
                    IngredientEffectCrossRef(
                        ingredientId = ingredientId.toInt(),
                        effectId = effectId.toInt()
                    )
                )
            }
        }
    }
}

