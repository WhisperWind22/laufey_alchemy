package com.example.alchemy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alchemy.data.model.Effect
import com.example.alchemy.data.model.Ingredient
import com.example.alchemy.data.model.IngredientWithEffects
import com.example.alchemy.data.repository.AlchemyRepository
import com.example.alchemy.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlchemyViewModel : ViewModel() {
    companion object {
        private const val TAG = "AlchemyViewModel"
    }

    private val _ingredients = MutableStateFlow<List<IngredientWithEffects>>(emptyList())
    val ingredients: StateFlow<List<IngredientWithEffects>> = _ingredients

    private val _selectedIngredients = MutableStateFlow<List<IngredientWithEffects>>(emptyList())
    val selectedIngredients: StateFlow<List<IngredientWithEffects>> = _selectedIngredients

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Main)
    val currentScreen: StateFlow<Screen> = _currentScreen

    private var repository: AlchemyRepository? = null

    fun initializeRepository(repo: AlchemyRepository) {
        Log.d(TAG, "initializeRepository: Initializing repository")
        repository = repo
        loadIngredients()
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    private fun loadIngredients() {
        Log.d(TAG, "loadIngredients: Starting to load ingredients")
        viewModelScope.launch {
            try {
                repository?.getAllIngredients()?.collect { ingredients ->
                    Log.d(TAG, "loadIngredients: Loaded ${ingredients.size} ingredients")
                    _ingredients.value = ingredients
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadIngredients: Error loading ingredients", e)
                _ingredients.value = emptyList()
            }
        }
    }

    fun addIngredientWithEffects(ingredient: Ingredient, effects: List<String>) {
        viewModelScope.launch {
            repository?.addIngredientWithEffects(
                ingredient = ingredient,
                effects = effects.map { Effect(name = it) },
                crossRefs = emptyList()
            )
        }
    }

    fun toggleIngredientSelection(ingredientWithEffects: IngredientWithEffects) {
        val currentSelected = _selectedIngredients.value.toMutableList()
        if (currentSelected.contains(ingredientWithEffects)) {
            currentSelected.remove(ingredientWithEffects)
        } else if (currentSelected.size < 3) {
            currentSelected.add(ingredientWithEffects)
        }
        _selectedIngredients.value = currentSelected
    }
}