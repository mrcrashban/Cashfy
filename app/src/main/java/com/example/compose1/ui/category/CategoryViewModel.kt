package com.example.compose1.ui.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.compose1.Graph
import com.example.compose1.R
import com.example.compose1.db.entities.Category
import com.example.compose1.db.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryViewModel(
        private val repository: Repository = Graph.repository
    ) :ViewModel(){
        var state by mutableStateOf(CategoryState())
          private set

    init {
        getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch {
            repository.category.collectLatest {
                state = state.copy(
                    categoryList = it
                )
            }
        }
    }

    val isFieldsNotEmpty:Boolean
        get() = state.categoryName.isNotEmpty()

    fun onCategoryChange(newValue:String){
        state = state.copy(categoryName = newValue)
    }

    fun deleteCategory(category: Category){
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

    fun onTypeChange(newValue:String){
        state = state.copy(categoriesType = newValue)
    }

    fun addCategory(imageId: Int){
        viewModelScope.launch {
            repository.insertCategory(
                Category(
                    categoryName = state.categoryName,
                    imageId = imageId
                )
            )
        }
    }

    fun updateCategory(id:Int, imageId: Int){
        viewModelScope.launch {
            repository.insertCategory(
                Category(
                    uidCategory = id,
                    categoryName = state.categoryName,
                    imageId = imageId
                )
            )
        }
    }
    }

data class CategoryState(
    val categoryList: List<Category> = emptyList(),
    val categoryName:String = "",
    val imageId: Int = 0,
    val isUpdatingCategory: Boolean = false,
    val categoriesType: String = ""
    )