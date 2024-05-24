package com.example.compose1.ui.addtransaction

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose1.R
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.ui.TransactionTypes
import com.example.compose1.ui.category.Categories
import com.example.compose1.ui.category.CategoryViewModel
import com.example.compose1.ui.main.formatDate
import java.util.Calendar
import java.util.Date


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTransactionScreen(
    id: Int,
    navigateUp: () -> Unit,
){
   val addTransactionViewModel = viewModel<AddTransactionViewModel>(factory = AddTransactionViewModelFactory(id))
   val addTransactionState = addTransactionViewModel.state
    Scaffold {
        Column {
            TransactionEntry(
                state = addTransactionState,
                onSumChange = addTransactionViewModel::onSumChange,
                onTypeChange = addTransactionViewModel::onTypeChange,
                onDateSelected = addTransactionViewModel::onDateChange,
                onCommentChange = addTransactionViewModel::onCommentChange,
                onCategoryChange = addTransactionViewModel::onCategoryChange,
                onCategorySelected = addTransactionViewModel::onCategorySelected,
                onAccountChange = addTransactionViewModel::onAccountChange,
                updateTransaction = { addTransactionViewModel.updateTransaction(id) },
                onSaveTransaction = addTransactionViewModel::addTransaction,
                deleteTransaction = { addTransactionViewModel.deleteTransaction(id) },
                navigateUp = navigateUp)
        }
    }
}

@Composable
private fun TransactionEntry(
    modifier: Modifier = Modifier,
    state: TransactionState,
    onSumChange: (String) -> Unit,
    onTypeChange: (TransactionTypes) -> Unit,
    onDateSelected: (Date) -> Unit,
    onCommentChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onAccountChange: (String) -> Unit,
    updateTransaction: () -> Unit,
    onSaveTransaction: () -> Unit,
    deleteTransaction: () -> Unit,
    navigateUp: () -> Unit
){
    var selectedType by remember { mutableStateOf(state.type) }
    val categoryViewModel = viewModel(modelClass = CategoryViewModel::class.java)
    val categoryState = categoryViewModel.state
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.app_background))
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(start = 5.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 25.sp,
            text = stringResource(id = R.string.add_transaction)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(color = Color.White)
        ) {
            TransactionTypeButton(
                text = stringResource(id = R.string.income),
                isSelected = selectedType == TransactionTypes.income,
                onClick = {
                    selectedType = TransactionTypes.income
                    onTypeChange(TransactionTypes.income)
                },
                modifier = Modifier.weight(1f)
            )
            TransactionTypeButton(
                text = stringResource(id = R.string.outcome),
                isSelected = selectedType == TransactionTypes.outcome,
                onClick = {
                    selectedType = TransactionTypes.outcome
                    onTypeChange(TransactionTypes.outcome)
                },
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp, bottom = 13.dp),
            fontSize = 20.sp,
            text = stringResource(id = R.string.input_sum)
        )
        TextField(
            value = state.sum,
            onValueChange = { onSumChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(id = R.string.transaction_sum)) },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            maxLines = 1,
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
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp, bottom = 3.dp),
            fontSize = 20.sp,
            text = stringResource(id = R.string.choose_account)
        )
        Account_ExposedDropdownMenuBox(
            accounts = state.accountList,
            onAccountChange = onAccountChange,
            initialAccount = state.account
        )
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp, bottom = 3.dp),
            fontSize = 20.sp,
            text = stringResource(id = R.string.choose_category)
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            verticalItemSpacing = 5.dp,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.Transparent)
        ) {
            items(state.categoryList) { category ->
                val isSelected = category == state.selectedCategory
                Categories(
                    category = category,
                    isSelected = isSelected, // Передаем состояние выбранности
                    onCategoryClick = {
                        onCategoryChange(category.categoryName)
                        onCategorySelected(category)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.choose_date),
                    Modifier.padding(start = 10.dp ,end = 20.dp),
                    fontSize = 20.sp,
                )
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = formatDate(state.date),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(4.dp))
                val mDatePicker = datePickerDialog(
                    context = LocalContext.current,
                    onDateSelected = { date ->
                        onDateSelected.invoke(date)
                    }
                )
                IconButton(onClick = { mDatePicker.show() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp, bottom = 13.dp),
            fontSize = 20.sp,
            text = stringResource(id = R.string.write_comment)
        )
        TextField(
            value = state.comment,
            onValueChange = { onCommentChange(it) },
            label = { Text(text = stringResource(id = R.string.comment)) },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
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
        Spacer(modifier = Modifier.size(15.dp))
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
                        when (state.isUpdatingTransaction) {
                            true -> updateTransaction.invoke()
                            false -> onSaveTransaction.invoke()
                        }
                        navigateUp.invoke()
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
                    enabled = state.sum.isNotEmpty()
                            && state.account.isNotEmpty()
                            && state.category.isNotEmpty()
                            && state.comment.isNotEmpty()
                            && state.date.toString().isNotEmpty()
                ) {
                    Text(
                        text = if (state.isUpdatingTransaction) stringResource(id = R.string.update_transaction_btn)
                        else stringResource(id = R.string.add_transaction_btn),
                        fontSize = 20.sp
                    )
                }

                if (state.isUpdatingTransaction) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            deleteTransaction.invoke()
                            navigateUp.invoke()
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(30.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Red
                        ),
                        border = BorderStroke(1.dp, Color.Red)
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete_transaction_btn),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun datePickerDialog(
    context: Context,
    onDateSelected: (Date) -> Unit,
): DatePickerDialog {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayofMonth: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(mYear, mMonth, mDayofMonth)
            onDateSelected.invoke(calendar.time)
        }, year, month, day
    )
    return mDatePickerDialog
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Category_ExposedDropdownMenuBox(
    categories: List<Category>,
    onCategoryChange: (String) -> Unit,
    initialCategory: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(initialCategory ?: categories.firstOrNull()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 15.dp, bottom = 3.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = initialCategory ?: "",
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(fontSize = 16.sp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Black,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.Black,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category.categoryName) },
                        onClick = {
                            selectedCategory = category
                            onCategoryChange(category.categoryName)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account_ExposedDropdownMenuBox(
    accounts: List<Account>,
    onAccountChange: (String) -> Unit,
    initialAccount: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf(initialAccount ?: accounts.firstOrNull()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 15.dp, bottom = 3.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = initialAccount ?: "",
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(fontSize = 16.sp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Black,
                    focusedLabelColor = Color.LightGray,
                    unfocusedLabelColor = Color.Black,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                accounts.forEach { account ->
                    DropdownMenuItem(
                        text = { Text(text = account.accountName) },
                        onClick = {
                            selectedAccount = account
                            onAccountChange(account.accountName)
                            expanded = false
                        }
                    )
                }
            }
        }
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