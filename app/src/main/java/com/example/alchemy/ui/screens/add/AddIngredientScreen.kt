// File: AddIngredientMenu.kt
package com.example.alchemy.ui.screens.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.alchemy.R
import com.example.alchemy.data.model.Ingredient

@Composable
fun AddIngredientMenu(
    onAddIngredient: (Ingredient, List<String>) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var materialEquivalent by remember { mutableStateOf("") }
    var mainEffect by remember { mutableStateOf("") }
    var additionalEffects by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF3F51B5))
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Название ингредиента", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color(0x33FFFFFF)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = materialEquivalent,
                onValueChange = { materialEquivalent = it },
                label = { Text("Материальный эквивалент", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color(0x33FFFFFF)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = mainEffect,
                onValueChange = { mainEffect = it },
                label = { Text("Основной эффект", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color(0x33FFFFFF)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = additionalEffects,
                onValueChange = { additionalEffects = it },
                label = { Text("Дополнительные эффекты (через запятую)", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color(0x33FFFFFF)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (name.isNotBlank() && materialEquivalent.isNotBlank() && mainEffect.isNotBlank()) {
                        val ingredient = Ingredient(
                            id = 0,
                            name = name,
                            materialEquivalent = materialEquivalent,
                            mainEffect = mainEffect
                        )
                        val effects = additionalEffects.split(",")
                            .map { it.trim() }
                            .filter { it.isNotBlank() }
                        onAddIngredient(ingredient, effects)
                        onBack()
                    }
                },
                enabled = name.isNotBlank() && materialEquivalent.isNotBlank() && mainEffect.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить ингредиент")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Назад")
            }
        }
    }
}