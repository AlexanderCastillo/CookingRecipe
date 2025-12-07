package com.example.cookingrecipe.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cookingrecipe.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy


@Dao
interface RecipeDao {
    /**
     * Obtiene las recetas filtradas por el titulo
     *
     * @param title para filtrar recetas por titulo
     */
    @Query("select * from recipes where title like '%' || :title || '%' order by title asc")
    fun getByTitle(title: String): Flow<List<Recipe>>

    /**
     * Obtiene la receta por id
     *
     * @param id para filtrar receta por id
     */
    @Query("select * from recipes where id = :id")
    fun getById(id: Int): Flow<Recipe>

    /**
     * Inserta una receta
     *
     * @param recipe para insertar
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)
}