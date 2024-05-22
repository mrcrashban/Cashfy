package com.example.compose1.ui.category

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose1.R
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
            Column (
                modifier = Modifier.background(colorResource(id = R.color.app_background))
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.size(it.calculateTopPadding()))
                TypeButtons(state = categoryState, onTypeChange = categoryViewModel::onTypeChange)
                CategoryEntry(
                    state = categoryViewModel.state,
                    onCategoryChange = categoryViewModel::onCategoryChange,
                    onSaveCategory = categoryViewModel::addCategory,
                    modifier = Modifier.fillMaxHeight(0.2f)
                ) {
                    navController.navigate("MainScreen")
                }
                /*LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                    verticalItemSpacing = 5.dp,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                ) {
                    items(categoryState.categoryList) {
                        Categories(category = it) {
                            categoryViewModel.deleteCategory(it)
                        }
                    }
                }*/
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp)
                        .wrapContentHeight(Alignment.Bottom)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(60.dp)
                                .clip(RoundedCornerShape(30.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = colorResource(id = R.color.soft_green)
                            ),
                            border = BorderStroke(1.dp, colorResource(id = R.color.soft_green)),
                        ) {
                            Text(
                                text = "Добавить категорию",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TypeButtons(
    state: CategoryState,
    onTypeChange: (String) -> Unit,
){
    var selectedType by remember { mutableStateOf(state.categoriesType) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
            .background(color = Color.White)
    ) {
        TransactionTypeButton(
            text = "Доходы",
            isSelected = selectedType == "income",
            onClick = {
                selectedType = "income"
                onTypeChange("income")
            },
            modifier = Modifier.weight(1f)
        )
        TransactionTypeButton(
            text = "Расходы",
            isSelected = selectedType == "outcome",
            onClick = {
                selectedType = "outcome"
                onTypeChange("outcome")
            },
            modifier = Modifier.weight(1f)
        )
    }
}
@Composable
private fun CategoryEntry(
    modifier: Modifier = Modifier,
    state: CategoryState,
    onCategoryChange:(String) -> Unit,
    onSaveCategory:(Int) -> Unit,
    navController: () -> Unit
){
    var selectedImageId by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp, bottom = 13.dp),
            fontSize = 20.sp,
            text = "Введите название категории"
        )
        TextField(
            value = state.categoryName,
            onValueChange = {onCategoryChange(it)},
            label = { Text(text = "категория")},
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedLabelColor = Color.LightGray,
                unfocusedLabelColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.LightGray
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp, bottom = 13.dp),
            fontSize = 20.sp,
            text = "Выберите иконку категории"
        )
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.baseline_directions_car_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 1 }
                .border(
                    if (selectedImageId == 1) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_sports_basketball_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 2 }
                .border(
                    if (selectedImageId == 2) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_house_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 3 }
                .border(
                    if (selectedImageId == 3) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_library_music_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 4 }
                .border(
                    if (selectedImageId == 4) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
        }
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.baseline_directions_bus_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 6 }
                .border(
                    if (selectedImageId == 6) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_card_giftcard_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 7 }
                .border(
                    if (selectedImageId == 7) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_family_restroom_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 8 }
                .border(
                    if (selectedImageId == 8) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_local_cafe_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 9 }
                .border(
                    if (selectedImageId == 9) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
        }
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.baseline_sports_esports_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 11 }
                .border(
                    if (selectedImageId == 11) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_monitor_heart_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 12 }
                .border(
                    if (selectedImageId == 12) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_question_mark_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 13 }
                .border(
                    if (selectedImageId == 13) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_fastfood_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 10 }
                .border(
                    if (selectedImageId == 10) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
        }
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Image(painter = painterResource(id = R.drawable.outline_shopping_cart_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 5 }
                .border(
                    if (selectedImageId == 5) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_book_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 14 }
                .border(
                    if (selectedImageId == 14) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_chair_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 15 }
                .border(
                    if (selectedImageId == 15) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
            Image(painter = painterResource(id = R.drawable.baseline_more_horiz_78), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
                .clickable { selectedImageId = 16 }
                .border(
                    if (selectedImageId == 16) BorderStroke(
                        1.dp,
                        Color.Blue
                    ) else BorderStroke(0.dp, Color.Transparent)
                ))
        }

       /* val buttonTitle = if (state.isUpdatingCategory) "Update Category"
        else "Add Category"
        Button(
            onClick = {
                when (state.isUpdatingCategory) {
                    true -> {
                        // onUpdateCategory.invoke()
                    }

                    false -> {
                        onSaveCategory(selectedImageId)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.categoryName.isNotEmpty(),
        ) {
            Text(text = buttonTitle)
        }*/
    }
}


@Composable
fun Categories(
    category: Category,
    onCategoryClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.app_background))
            .clickable {
                onCategoryClick.invoke()
            }
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = getImageResource(category.imageId)),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
            )
            Text(
                text = category.categoryName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


fun getImageResource(imageId: Int): Int {
    return when (imageId) {
        1 -> R.drawable.baseline_directions_car_78
        2 -> R.drawable.baseline_sports_basketball_78
        3 -> R.drawable.baseline_house_78
        4 -> R.drawable.baseline_library_music_78
        5 -> R.drawable.outline_shopping_cart_78
        6 -> R.drawable.baseline_directions_bus_78
        7 -> R.drawable.baseline_card_giftcard_78
        8 -> R.drawable.baseline_family_restroom_78
        9 -> R.drawable.baseline_local_cafe_78
        10 -> R.drawable.baseline_fastfood_78
        11 -> R.drawable.baseline_sports_esports_78
        12 -> R.drawable.baseline_monitor_heart_78
        13 -> R.drawable.baseline_question_mark_78
        else -> R.drawable.baseline_broken_image_24
    }
}

@Composable
private fun TransactionTypeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) Color.Black else Color.LightGray
    val underlineModifier = if (isSelected) {
        Modifier
            .padding(top = 5.dp)
            .height(2.dp)
            .fillMaxWidth()
            .background(Color.Black)
    } else {
        Modifier.height(0.dp)
    }

    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .padding(horizontal = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text,
                fontSize = 20.sp,
                color = contentColor,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Box(modifier = underlineModifier)
        }
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