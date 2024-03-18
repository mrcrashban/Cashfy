package com.example.compose1.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.compose1.db.entities.Account
import com.example.compose1.db.entities.Category
import com.example.compose1.db.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("""
        SELECT * FROM `transaction` AS T INNER JOIN category AS C
        ON T.category_id = C.uidCategory INNER JOIN account AS A
        ON T.account_id = A.uidAccount
        """)
    fun getTransactionsWithAccountAndCategory(): Flow<List<TransactionsWithAccountAndCategory>>

    @Query("""
        SELECT * FROM `transaction` WHERE uidTransaction =:transactionId
    """)
    fun getTransaction(transactionId:Int):Flow<Transaction>
}

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("SELECT * FROM account WHERE uidAccount =:accountId")
    fun getAccount(accountId:Int):Flow<Account>
}

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM category WHERE uidCategory =:categoryId")
    fun getCategory(categoryId:Int):Flow<Category>
}

data class TransactionsWithAccountAndCategory(
    @Embedded val transaction: Transaction,
    @Embedded val category: Category,
    @Embedded val account: Account
)