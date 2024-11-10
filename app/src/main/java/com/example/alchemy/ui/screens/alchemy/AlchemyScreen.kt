package com.example.alchemy.ui.screens.alchemy

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.alchemy.data.model.IngredientWithEffects
import com.example.alchemy.ui.components.IngredientItem
import com.example.alchemy.ui.viewmodel.AlchemyViewModel

@Composable
fun AlchemyScreen(
    viewModel: AlchemyViewModel,
    context: Context,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val ingredients = viewModel.ingredients.collectAsStateWithLifecycle(initialValue = emptyList())
    val selectedIngredients =
        viewModel.selectedIngredients.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0x88000000))
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Поисковая строка
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск по названию или эффекту", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color(0x33FFFFFF),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        SelectedIngredientsSection(selectedIngredients.value)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = calculatePotionEffect(selectedIngredients.value),
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0x33FFFFFF))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val filteredIngredients = ingredients.value.filter { ingredient ->
            val query = searchQuery.lowercase()
            ingredient.ingredient.name.lowercase().contains(query) ||
                    ingredient.ingredient.mainEffect.lowercase().contains(query) ||
                    ingredient.additionalEffects.any { it.name.lowercase().contains(query) }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(filteredIngredients) { ingredientWithEffects ->
                IngredientItem(
                    ingredientWithEffects = ingredientWithEffects,
                    isSelected = selectedIngredients.value.contains(ingredientWithEffects),
                    onSelect = { viewModel.toggleIngredientSelection(ingredientWithEffects) },
                    context = context
                )
            }
        }
    }
}

@Composable
fun SelectedIngredientsSection(selectedIngredients: List<IngredientWithEffects>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0x33FFFFFF))
    ) {
        Text(
            text = "Выбранные ингредиенты (${selectedIngredients.size}/3):",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        selectedIngredients.forEach { ingredientWithEffects ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "• ${ingredientWithEffects.ingredient.name}",
                    color = Color.White
                )
            }
        }
    }
}

fun calculatePotionEffect(ingredients: List<IngredientWithEffects>): String {
    if (ingredients.isEmpty()) return "Нет выбранных ингредиентов"

    val mainEffects = ingredients.map { it.ingredient.mainEffect }.distinct()
    val additionalEffectsCount = ingredients.flatMap { it.additionalEffects }
        .groupingBy { it.name }
        .eachCount()

    val enhancedEffects = additionalEffectsCount
        .filter { it.value >= 2 }
        .keys

    val normalEffects = additionalEffectsCount
        .filter { it.value == 1 }
        .keys

    return buildString {
        if (mainEffects.isNotEmpty()) {
            append("Основные эффекты:\n")
            mainEffects.forEach { effect ->
                append("• $effect ⚡\n")
            }
            append("\n")
        }

        if (enhancedEffects.isNotEmpty()) {
            append("Усиленные дополнительные эффекты:\n")
            enhancedEffects.forEach { effect ->
                append("• $effect ✨\n")
            }
            append("\n")
        }

        if (normalEffects.isNotEmpty()) {
            append("Обычные дополнительные эффекты:\n")
            normalEffects.forEach { effect ->
                append("• $effect\n")
            }
        }

        if (mainEffects.isEmpty() && enhancedEffects.isEmpty() && normalEffects.isEmpty()) {
            append("Нет активных эффектов")
        }
    }
}