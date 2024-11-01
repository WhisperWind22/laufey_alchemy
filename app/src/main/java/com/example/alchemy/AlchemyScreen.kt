// File: AlchemyScreen.kt
package com.example.alchemy

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AlchemyScreen(
    alchemyRepository: AlchemyRepository, ingredients: List<Ingredient>, context: Context
) {
    val coroutineScope = rememberCoroutineScope()
    val ingredientsState = remember { mutableStateOf<List<Ingredient>?>(null) }
    val selectedIngredients = remember { mutableStateOf<List<Ingredient>>(emptyList()) }
    val allIngredients = remember { mutableStateOf<List<Ingredient>>(emptyList()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            ingredientsState.value = CSVUtils.loadIngredientsFromCSV(context)
            allIngredients.value = ingredientsState.value.orEmpty().toMutableList().apply {
                addAll(ingredients.filter { it !in this })
            }
        }
    }

    LaunchedEffect(ingredients) {
        allIngredients.value = ingredientsState.value.orEmpty().toMutableList().apply {
            addAll(ingredients.filter { it !in this })
        }
    }

    if (ingredientsState.value != null) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.LightGray)
        ) {
            LazyColumn {
                items(allIngredients.value) { ingredient ->
                    val resourceId = context.resources.getIdentifier(
                        ingredient.imageUrl, "drawable", context.packageName
                    )
                    if (resourceId != 0) {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = resourceId),
                                contentDescription = ingredient.name,
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = ingredient.name, modifier = Modifier.clickable {
                                if (selectedIngredients.value.size < 3 || selectedIngredients.value.contains(
                                        ingredient
                                    )
                                ) {
                                    selectedIngredients.value =
                                        selectedIngredients.value.toMutableList().apply {
                                            if (contains(ingredient)) remove(ingredient) else add(
                                                ingredient
                                            )
                                        }
                                }
                            })
                        }
                    } else {
                        Log.e("AlchemyScreen", "Resource not found for ${ingredient.imageUrl}")
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .background(Color.DarkGray)
            )
            Text(text = "Potion Effect: ${calculatePotionEffect(selectedIngredients.value)}")
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            LoadingScreen()
        }
    }
}

fun calculatePotionEffect(ingredients: List<Ingredient>): String {
    // Simple example of combining effects
    return ingredients.joinToString(", ") { it.primaryEffect }
}