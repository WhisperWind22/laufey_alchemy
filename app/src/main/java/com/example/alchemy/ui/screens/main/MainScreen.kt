package com.example.alchemy.ui.screens.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.alchemy.R
import com.example.alchemy.navigation.Screen
import com.example.alchemy.ui.viewmodel.AlchemyViewModel

@Composable
fun MainScreen(
    viewModel: AlchemyViewModel,
    context: Context,
    onNavigate: (Screen) -> Unit
) {
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NavigationButtons(
                onAlchemyClick = { onNavigate(Screen.Alchemy) },
                onAddIngredientClick = { onNavigate(Screen.AddIngredient) },
                onShowIngredientListClick = { onNavigate(Screen.IngredientList) }
            )
        }
    }
}

@Composable
private fun NavigationButtons(
    onAlchemyClick: () -> Unit,
    onAddIngredientClick: () -> Unit,
    onShowIngredientListClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0x88000000))
    ) {
        Button(
            onClick = onAlchemyClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Алхимия", color = Color.White)
        }
        Button(
            onClick = onAddIngredientClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Добавить ингредиент", color = Color.White)
        }
        Button(
            onClick = onShowIngredientListClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Список ингредиентов", color = Color.White)
        }
    }
} 