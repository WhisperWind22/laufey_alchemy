// File: CSVUtils.kt
package com.example.alchemy

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object CSVUtils {
    fun loadIngredientsFromCSV(context: Context): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        val inputStream = context.assets.open("ingredients.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val values = line.split(";")
                val ingredient = Ingredient(
                    id = values[0].toInt(),
                    name = values[1],
                    primaryEffect = values[2],
                    secondaryEffect = values[3],
                    tertiaryEffect = values[4],
                    quaternaryEffect = values[5],
                    description = values[6],
                    imageUrl = values[7]
                )
                ingredients.add(ingredient)
            }
        }
        return ingredients
    }
}