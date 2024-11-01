package com.example.alchemy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IngredientDao {
    @Query("SELECT * FROM Ingredient WHERE primaryEffect LIKE :effect OR secondaryEffect LIKE :effect OR tertiaryEffect LIKE :effect")
    fun getIngredientsByEffect(effect: String): List<Ingredient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg ingredients: Ingredient)
}