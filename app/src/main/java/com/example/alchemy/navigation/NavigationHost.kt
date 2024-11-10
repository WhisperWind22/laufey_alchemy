package com.example.alchemy.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.alchemy.ui.screens.add.AddIngredientMenu
import com.example.alchemy.ui.screens.alchemy.AlchemyScreen
import com.example.alchemy.ui.screens.ingredientlist.IngredientListScreen
import com.example.alchemy.ui.screens.main.MainScreen
import com.example.alchemy.ui.viewmodel.AlchemyViewModel

@Composable
fun NavigationHost(
    currentScreen: Screen,
    viewModel: AlchemyViewModel,
    context: Context
) {
    when (currentScreen) {
        Screen.Main -> MainScreen(
            viewModel = viewModel,
            context = context,
            onNavigate = viewModel::navigateTo
        )

        Screen.Alchemy -> AlchemyScreen(
            viewModel = viewModel,
            context = context,
            onBack = { viewModel.navigateTo(Screen.Main) }
        )

        Screen.IngredientList -> IngredientListScreen(
            ingredients = viewModel.ingredients.collectAsStateWithLifecycle().value,
            onBack = { viewModel.navigateTo(Screen.Main) },
            context = context
        )

        Screen.AddIngredient -> AddIngredientMenu(
            onAddIngredient = viewModel::addIngredientWithEffects,
            onBack = { viewModel.navigateTo(Screen.Main) }
        )
    }
} 