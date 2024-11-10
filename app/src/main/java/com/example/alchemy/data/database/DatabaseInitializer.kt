package com.example.alchemy.data.database

import android.content.Context
import android.util.Log
import com.example.alchemy.data.repository.AlchemyRepository
import com.example.alchemy.utils.CSVUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object DatabaseInitializer {
    private const val TAG = "DatabaseInitializer"

    fun initializeDatabase(context: Context, repository: AlchemyRepository) {
        Log.d(TAG, "initializeDatabase: Starting database initialization")
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val currentIngredients = repository.getAllIngredients().first()

                if (currentIngredients.isEmpty()) {
                    val ingredientsData = CSVUtils.loadIngredientsFromCSV(context)

                    ingredientsData.forEach { data ->
                        repository.addIngredientWithEffects(
                            data.ingredient,
                            data.effects,
                            data.crossRefs
                        )
                    }
                }
            }
            Log.d(TAG, "initializeDatabase: Database initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "initializeDatabase: Error initializing database", e)
        }
    }
} 