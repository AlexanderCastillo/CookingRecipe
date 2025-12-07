package com.example.cookingrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.cookingrecipe.data.database.AppDatabase
import com.example.cookingrecipe.data.repository.RecipeRepository
import com.example.cookingrecipe.ui.screens.RecipeListScreen
import com.example.cookingrecipe.ui.theme.CookingRecipeTheme
import com.example.cookingrecipe.ui.viewmodel.RecipeViewModel
import com.example.cookingrecipe.ui.viewmodel.RecipeViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    private val repository by lazy {
        RecipeRepository(database.recipeDao())
    }

    private val viewModel: RecipeViewModel by viewModels{
        RecipeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookingRecipeTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
