package com.example.alchemy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlchemyRepository(private val ingredientDao: IngredientDao) {

    suspend fun findIdealPotion(effect: String): List<Ingredient> {
        return withContext(Dispatchers.IO) {
            ingredientDao.getIngredientsByEffect(effect)
        }
    }
    fun addIngredient(ingredient: Ingredient) {
        ingredientDao.insertAll(ingredient)
    }
}