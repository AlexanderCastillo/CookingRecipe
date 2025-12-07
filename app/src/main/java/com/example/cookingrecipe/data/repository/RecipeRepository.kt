package com.example.cookingrecipe.data.repository

import com.example.cookingrecipe.data.dao.RecipeDao
import com.example.cookingrecipe.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class RecipeRepository (private val recipeDao: RecipeDao){

    fun getByTitle(title: String): Flow<List<Recipe>> = recipeDao.getByTitle(title)

    fun getById(id: Int): Flow<Recipe> = recipeDao.getById(id)

    suspend fun insert(recipe: Recipe) = recipeDao.insert(recipe)

}