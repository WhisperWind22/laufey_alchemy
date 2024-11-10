package com.example.alchemy.ui.screens.ingredientlist

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alchemy.data.model.IngredientWithEffects
import com.example.alchemy.ui.components.IngredientItem

@Composable
fun IngredientListScreen(
    ingredients: List<IngredientWithEffects>,
    onBack: () -> Unit,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(ingredients) { ingredient ->
                IngredientItem(
                    ingredientWithEffects = ingredient,
                    isSelected = false,
                    onSelect = { },
                    context = context
                )
            }
        }
    }
} 