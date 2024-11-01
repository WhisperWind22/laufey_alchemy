// File: AddIngredientMenu.kt
package com.example.alchemy

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddIngredientMenu(onAddIngredient: (Ingredient) -> Unit, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var primaryEffect by remember { mutableStateOf("") }
    var secondaryEffect by remember { mutableStateOf("") }
    var tertiaryEffect by remember { mutableStateOf("") }
    var quaternaryEffect by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Название ингредиента") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = primaryEffect, onValueChange = { primaryEffect = it }, label = { Text("Основной эффект") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = secondaryEffect, onValueChange = { secondaryEffect = it }, label = { Text("Вторичный эффект") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = tertiaryEffect, onValueChange = { tertiaryEffect = it }, label = { Text("Третичный эффект") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = quaternaryEffect, onValueChange = { quaternaryEffect = it }, label = { Text("Четвертичный эффект") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = description, onValueChange = { description = it }, label = { Text("Описание") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("URL изображения") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val ingredient = Ingredient(
                id = 0,
                name = name,
                primaryEffect = primaryEffect,
                secondaryEffect = secondaryEffect,
                tertiaryEffect = tertiaryEffect,
                quaternaryEffect = quaternaryEffect,
                description = description,
                imageUrl = imageUrl
            )
            onAddIngredient(ingredient)
            onBack()
        }) {
            Text("Добавить ингредиент")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}