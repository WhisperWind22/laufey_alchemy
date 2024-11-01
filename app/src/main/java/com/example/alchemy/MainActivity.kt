// File: MainActivity.kt
package com.example.alchemy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import coil.compose.rememberImagePainter

class MainActivity : AppCompatActivity() {

    private val viewModel: AlchemyViewModel by viewModels {
        AlchemyViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")

        setContent {
            Log.d("MainActivity", "Setting content")
            MainScreen(viewModel, context = this)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")
    }
}

@Composable
fun MainScreen(viewModel: AlchemyViewModel, context: Context) {
    val alchemyRepository by viewModel.alchemyRepository.observeAsState()
    var showAddIngredientMenu by remember { mutableStateOf(false) }
    val ingredients = remember { mutableStateOf<List<Ingredient>>(emptyList()) }

    LaunchedEffect(Unit) {
        ingredients.value = CSVUtils.loadIngredientsFromCSV(context)
    }

    when {
        showAddIngredientMenu -> {
            AddIngredientMenu(
                onAddIngredient = { ingredient ->
                    ingredients.value = ingredients.value + ingredient
                    alchemyRepository?.addIngredient(ingredient)
                    showAddIngredientMenu = false
                },
                onBack = { showAddIngredientMenu = false }
            )
        }

        else -> {
            MainScreenContent(
                alchemyRepository = alchemyRepository,
                ingredients = ingredients.value,
                onAddIngredientClick = { showAddIngredientMenu = true },
                onShowIngredientListClick = {
                    val intent = Intent(context, IngredientListActivity::class.java)
                    context.startActivity(intent)
                },
                context = context
            )
        }
    }
}

@Composable
fun MainScreenContent(
    alchemyRepository: AlchemyRepository?,
    ingredients: List<Ingredient>,
    onAddIngredientClick: () -> Unit,
    onShowIngredientListClick: () -> Unit,
    context: Context
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.padding(16.dp)) {
            if (alchemyRepository != null) {
                Log.d("MainScreen", "alchemyRepository != null")
                AlchemyScreen(
                    alchemyRepository = alchemyRepository,
                    ingredients = ingredients,
                    context = context
                )
            } else {
                // Display a loading or error state if the repository is not yet initialized
                LoadingScreen()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAddIngredientClick,
                modifier = Modifier.background(Color.Transparent)
            ) {
                Text("Добавить ингредиент")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onShowIngredientListClick,
                modifier = Modifier.background(Color.Transparent)
            ) {
                Text("Список ингридиентов")
            }
        }
    }
}

@Composable
fun IngredientListScreen(
    ingredients: List<Ingredient>,
    onBack: () -> Unit,
    context: Context
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Column {
            Button(onClick = onBack) {
                Text("Назад")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ingredients.forEach { ingredient ->
                val resourceId = context.resources.getIdentifier(
                    ingredient.imageUrl,
                    "drawable",
                    context.packageName
                )
                if (resourceId != 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = resourceId),
                            contentDescription = ingredient.name,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = ingredient.name)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                } else {
                    Log.e("IngredientListScreen", "Resource not found for ${ingredient.imageUrl}")
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    // Simple loading screen implementation
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val dummyRepository = AlchemyRepository(object : IngredientDao {
        override fun getIngredientsByEffect(effect: String): List<Ingredient> {
            return listOf(
                Ingredient(1, "Ingredient 1", effect, "", "", "", "", ""),
                Ingredient(2, "Ingredient 2", effect, "", "", "", "", ""),
            )
        }

        override fun insertAll(vararg ingredients: Ingredient) {
            // No-op
        }
    })
    MainScreen(viewModel = AlchemyViewModel(MockApplication()).apply {
        setAlchemyRepositoryForTesting(dummyRepository)
    }, context = MockApplication())
}