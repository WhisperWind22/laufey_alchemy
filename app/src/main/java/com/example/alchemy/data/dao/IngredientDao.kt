package com.example.alchemy.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.alchemy.data.model.Effect
import com.example.alchemy.data.model.Ingredient
import com.example.alchemy.data.model.IngredientEffectCrossRef
import com.example.alchemy.data.model.IngredientWithEffects
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Transaction
    @Query("SELECT * FROM ingredients WHERE main_effect LIKE '%' || :effect || '%'")
    fun getIngredientsByEffect(effect: String): List<IngredientWithEffects>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(ingredient: Ingredient): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEffect(effect: Effect): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(crossRef: IngredientEffectCrossRef)

    @Transaction
    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flow<List<IngredientWithEffects>>

    @Delete
    fun deleteIngredient(ingredient: Ingredient)

    @Transaction
    @Query("SELECT * FROM ingredients WHERE id = :id")
    fun getIngredientById(id: Int): IngredientWithEffects?

    @Query("SELECT * FROM effects WHERE name = :name")
    fun getEffectByName(name: String): Effect?
} 