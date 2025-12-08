package com.example.cookingrecipe.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookingrecipe.ui.viewmodel.RecipeViewModel
import kotlin.text.isBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    viewModel: RecipeViewModel,
    onNavigateBack: () -> Unit
) {
    // Estados locales para los campos del formulario
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Nueva Receta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo para el Título
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false // Limpia el error al escribir
                },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                isError = titleError,
                singleLine = true
            )
            if (titleError) {
                Text("El título no puede estar vacío", color = MaterialTheme.colorScheme.error)
            }

            // Campo para la descripción
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Campo para los Ingredientes
            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredientes (separados por coma)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Campo para el Procedimiento
            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Procedimiento") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            //Spacer(modifier = Modifier.weight(1f))

            // Botón para Guardar
            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    } else {
                        // Llamar a la función del ViewModel para agregar la receta
                        viewModel.addRecipe(
                            title = title,
                            description = description,
                            ingredients = ingredients,
                            instructions = instructions
                        )
                        // Regresar a la pantalla anterior después de guardar
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Receta")
            }
        }
    }
}