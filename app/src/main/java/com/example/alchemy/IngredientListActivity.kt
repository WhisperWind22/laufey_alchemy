// File: IngredientListActivity.kt
package com.example.alchemy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class IngredientListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ingredients = CSVUtils.loadIngredientsFromCSV(this)
            IngredientListScreen(
                ingredients = ingredients,
                onBack = { finish() }
            )
        }
    }
}

@Composable
fun IngredientListScreen(ingredients: List<Ingredient>, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack, modifier = Modifier.padding(bottom = 8.dp)) {
            Text(text = "Назад")
        }
        LazyColumn {
            items(ingredients) { ingredient ->
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = "Название: ${ingredient.name}")
                    Text(text = "Основной эффект: ${ingredient.primaryEffect}")
                    Text(text = "Вторичный эффект: ${ingredient.secondaryEffect}")
                    Text(text = "Третий эффект: ${ingredient.tertiaryEffect}")
                    Text(text = "Побочный эффект: ${ingredient.quaternaryEffect}")
                    Text(text = "Описание: ${ingredient.description}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}