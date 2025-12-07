package com.example.cookingrecipe.ui.viewmodel

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookingrecipe.data.model.Recipe
import com.example.cookingrecipe.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    val recipe: StateFlow<List<Recipe>> = search
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getByTitle("")
            } else {
                repository.getByTitle(query)
            }
        }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    fun onSearchChange(newText: String){
        _search.value = newText
    }

    fun addRecipe(title: String, description: String, ingredients: String, instructions: String){
        viewModelScope.launch {
            repository.insert(
                Recipe(
                    title = title,
                    description = description,
                    ingredients = ingredients,
                    instructions = instructions
                )
            )
        }
    }
}