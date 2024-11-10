package com.example.alchemy.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alchemy.data.model.IngredientWithEffects

@Composable
fun IngredientItem(
    ingredientWithEffects: IngredientWithEffects,
    isSelected: Boolean,
    onSelect: () -> Unit,
    context: Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onSelect),
        elevation = if (isSelected) 8.dp else 2.dp,
        backgroundColor = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ingredientWithEffects.ingredient.name,
                    style = MaterialTheme.typography.h6
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Основной эффект: ${ingredientWithEffects.ingredient.mainEffect}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            if (ingredientWithEffects.additionalEffects.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Дополнительные эффекты: ${ingredientWithEffects.additionalEffects.joinToString { it.name }}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }
        }
    }
} 