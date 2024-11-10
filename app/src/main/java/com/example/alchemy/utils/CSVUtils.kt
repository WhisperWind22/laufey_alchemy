package com.example.alchemy.utils

import android.content.Context
import com.example.alchemy.data.model.Effect
import com.example.alchemy.data.model.Ingredient
import com.example.alchemy.data.model.IngredientEffectCrossRef
import java.io.BufferedReader
import java.io.InputStreamReader

data class CSVIngredientData(
    val ingredient: Ingredient,
    val effects: List<Effect>,
    val crossRefs: List<IngredientEffectCrossRef>
)

object CSVUtils {
    fun loadIngredientsFromCSV(context: Context): List<CSVIngredientData> {
        return try {
            val ingredientsData = mutableListOf<CSVIngredientData>()
            val effectsMap = mutableMapOf<String, Effect>()
            var effectIdCounter = 1

            context.assets.open("ingredients.csv").use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
                    lines.drop(1).forEach { line ->
                        try {
                            val values = line.split(";")
                            if (values.size >= 5) {
                                val ingredient = Ingredient(
                                    id = 0,
                                    name = values[1].trim(),
                                    materialEquivalent = values[2].trim(),
                                    mainEffect = values[3].trim()
                                )

                                // Обрабатываем дополнительные эффекты
                                val effectNames = values[4].split(",").map { it.trim() }
                                    .filter { it.isNotBlank() }
                                val effects = mutableListOf<Effect>()
                                val crossRefs = mutableListOf<IngredientEffectCrossRef>()

                                effectNames.forEach { effectName ->
                                    val effect = effectsMap.getOrPut(effectName) {
                                        Effect(id = effectIdCounter++, name = effectName)
                                    }
                                    effects.add(effect)
                                    crossRefs.add(
                                        IngredientEffectCrossRef(
                                            ingredientId = ingredient.id,
                                            effectId = effect.id
                                        )
                                    )
                                }

                                ingredientsData.add(
                                    CSVIngredientData(
                                        ingredient,
                                        effects,
                                        crossRefs
                                    )
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            ingredientsData
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
} 