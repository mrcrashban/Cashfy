package com.example.compose1.ui.addtransaction

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.ui.category.CategoryViewModel
import com.example.compose1.ui.main.formatDate
import java.util.Calendar
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddTransactionScreen(
    navController: NavController
){
   val addTransactionViewModel = viewModel(modelClass = AddTransactionViewModel::class.java)
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
                onAccountChange = addTransactionViewModel::onAccountChange,
                onSaveTransaction = addTransactionViewModel::AddTransaction) {
                navController.navigate("MainScreen")
            }
        }

    }
}

@Composable
private fun TransactionEntry(
    modifier: Modifier = Modifier,
    state: TransactionState,
    onSumChange:(String) -> Unit,
    onTypeChange:(String) -> Unit,
    onDateSelected: (Date) -> Unit,
    onCommentChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onAccountChange: (String) -> Unit,
    onSaveTransaction: () -> Unit,
    navController: () -> Unit

){
    Column (
        modifier = Modifier.padding(10.dp)
    ){
        TextField(
            value = state.sum,
            onValueChange = {onSumChange(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Sum of transaction") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = formatDate(state.date))
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
            TextField(
                value = state.type,
                onValueChange = { onTypeChange(it) },
                label = { Text(text = "Type of transaction") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        TextField(
            value = state.comment,
            onValueChange = { onCommentChange(it) },
            label = { Text(text = "Comment") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(15.dp))
        Category_ExposedDropdownMenuBox(
            categories = state.categoryList,
            onCategoryChange = onCategoryChange
        )
        Account_ExposedDropdownMenuBox(
            accounts = state.accountList,
            onCategoryChange = onAccountChange
        )
        Spacer(modifier = Modifier.Companion.size(15.dp))
        Button(
            onClick = {
                onSaveTransaction.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.sum.isNotEmpty()
                    && state.account.isNotEmpty()
                    && state.category.isNotEmpty()
                    && state.comment.isNotEmpty()
                    && state.date.toString().isNotEmpty()
                    && state.type.isNotEmpty(),
        ) {
            Text(text = "Add transaction")
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
    onCategoryChange:(String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedCategory?.categoryName ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
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
    onCategoryChange:(String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf(accounts.firstOrNull()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedAccount?.accountName ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
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
                            onCategoryChange(account.accountName)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}