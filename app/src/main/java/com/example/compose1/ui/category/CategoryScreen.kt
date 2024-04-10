package com.example.compose1.ui.category

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose1.db.entities.Category
import com.example.compose1.ui.navigation.DrawerBody
import com.example.compose1.ui.navigation.DrawerHeader
import com.example.compose1.ui.navigation.TopAppBarContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryScreen(
    navController: NavController
){
    val categoryViewModel = viewModel(modelClass = CategoryViewModel::class.java)
    val categoryState = categoryViewModel.state
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(navController)
            }
        }
    ) {
        Scaffold (
            topBar = {
                TopAppBarContent(scope, drawerState)
            }
        ){
            Column {
                Spacer(modifier = Modifier.size(it.calculateTopPadding()))
                CategoryEntry(
                    state = categoryViewModel.state,
                    onCategoryChange = categoryViewModel::onCategoryChange,
                    onSaveCategory = categoryViewModel::addCategory,
                    modifier = Modifier.fillMaxHeight(0.2f)
                ) {
                    navController.navigate("MainScreen")
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    items(categoryState.categoryList) {
                        Categories(category = it) {
                            categoryViewModel.deleteCategory(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryEntry(
    modifier: Modifier = Modifier,
    state: CategoryState,
    onCategoryChange:(String) -> Unit,
    onSaveCategory:() -> Unit,
    navController: () -> Unit
){
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        TextField(
            value = state.categoryName,
            onValueChange = {onCategoryChange(it)},
            label = { Text(text = "Category")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.size(15.dp))
        val buttonTitle = if (state.isUpdatingCategory) "Update Category"
        else "Add Category"
        Button(
            onClick = {
                when (state.isUpdatingCategory) {
                    true -> {
                        // onUpdateCategory.invoke()
                    }

                    false -> {
                        onSaveCategory.invoke()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.categoryName.isNotEmpty(),
        ) {
            Text(text = buttonTitle)
        }
    }
}

@Composable
fun Categories(
    category: Category,
    onCategoryClick: () -> Unit
){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCategoryClick.invoke()
            }
            .padding(5.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = category.categoryName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevCategory(){
    CategoryEntry(
        state = CategoryState(),
        onCategoryChange = {},
        onSaveCategory = { /*TODO*/ }) {

    }
}