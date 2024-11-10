package com.example.alchemy

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import com.example.alchemy.data.database.AlchemyDatabase
import com.example.alchemy.data.database.DatabaseInitializer
import com.example.alchemy.data.repository.AlchemyRepository
import com.example.alchemy.navigation.NavigationHost
import com.example.alchemy.ui.theme.AlchemyTheme
import com.example.alchemy.ui.viewmodel.AlchemyViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val viewModel: AlchemyViewModel by viewModels()
    private lateinit var database: AlchemyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDatabase()
        setupContent()
    }

    private fun initializeDatabase() {
        try {
            database = Room.databaseBuilder(
                applicationContext,
                AlchemyDatabase::class.java,
                "alchemy_database"
            ).build()

            val repository = AlchemyRepository(database.ingredientDao())
            viewModel.initializeRepository(repository)
            DatabaseInitializer.initializeDatabase(this, repository)
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing database", e)
        }
    }

    private fun setupContent() {
        setContent {
            AlchemyTheme {
                val currentScreen = viewModel.currentScreen.collectAsStateWithLifecycle()
                NavigationHost(
                    currentScreen = currentScreen.value,
                    viewModel = viewModel,
                    context = this
                )
            }
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

