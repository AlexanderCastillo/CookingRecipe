package com.example.cookingrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.cookingrecipe.data.database.AppDatabase
import com.example.cookingrecipe.data.repository.RecipeRepository
import com.example.cookingrecipe.ui.screens.RecipeDetailScreen
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
        //enableEdgeToEdge()
        setContent {
            CookingRecipeTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //RecipeListScreen(viewModel = viewModel)
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: RecipeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "recipe_list") {
        // Ruta para la pantalla de lista
        composable("recipe_list") {
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId ->
                    // Navega a la pantalla de detalle pasando el ID de la receta
                    navController.navigate("recipe_detail/$recipeId")
                }
            )
        }

        // Ruta para la pantalla de detalle
        composable(
            route = "recipe_detail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            requireNotNull(recipeId) { "El ID de la receta no puede ser nulo" }

            RecipeDetailScreen(
                recipeId = recipeId,
                viewModel = viewModel,
                onNavigateBack = {
                    viewModel.clearSelectedRecipe() // Limpia la receta al volver
                    navController.popBackStack()
                }
            )
        }
    }
}
