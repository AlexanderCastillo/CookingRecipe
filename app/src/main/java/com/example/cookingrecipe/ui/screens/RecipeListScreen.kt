package com.example.cookingrecipe.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookingrecipe.data.model.Recipe
import com.example.cookingrecipe.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(viewModel: RecipeViewModel) {
    val recipes by viewModel.recipe.collectAsState()
    val searchText by viewModel.search.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Barra de Búsqueda ---
        OutlinedTextField(
            value = searchText,
            onValueChange = viewModel::onSearchChange, // Llama a la función del ViewModel
            label = { Text("Buscar por título...") },
            modifier = Modifier.fillMaxWidth()
        )

        // --- Botón para agregar recetas de prueba ---
        Button(
            onClick = {
                // Datos de ejemplo
                viewModel.addRecipe(
                    title = "Tacos al Pastor",
                    description = "Clásicos tacos mexicanos.",
                    ingredients = "Tortillas, Carne de cerdo, Piña, Cebolla, Cilantro",
                    instructions = "Marinar la carne, asarla en el trompo, cortar y servir en tortillas con los demás ingredientes."
                )
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Receta de Prueba")
        }

        // --- Lista de Recetas ---
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(recipes) { recipe ->
                RecipeItem(recipe = recipe)
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = recipe.title)
        // Aquí podrías añadir un botón o hacer el item clickable
        // para navegar a la pantalla de detalle.
    }
}