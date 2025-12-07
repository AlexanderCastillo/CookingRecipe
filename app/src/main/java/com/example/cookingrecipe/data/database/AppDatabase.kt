package com.example.cookingrecipe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cookingrecipe.data.dao.RecipeDao
import com.example.cookingrecipe.data.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun recipeDao(): RecipeDao
}