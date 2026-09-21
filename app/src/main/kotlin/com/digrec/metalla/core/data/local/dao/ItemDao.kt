package com.digrec.metalla.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.model.ItemWithProductAndSupplier
import kotlinx.coroutines.flow.Flow

/** Data Access Object for the [ItemEntity] table. */
@Dao
interface ItemDao {

    @Transaction
    @Query("SELECT * FROM items ORDER BY purchaseDate DESC")
    fun getItemsWithDetails(): Flow<List<ItemWithProductAndSupplier>>

    @Transaction
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemWithDetailsById(id: Long): Flow<ItemWithProductAndSupplier?>

    @Transaction
    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemWithDetailsByIdSync(id: Long): ItemWithProductAndSupplier?

    @Transaction
    @Query("SELECT * FROM items WHERE productId = :productId ORDER BY purchaseDate DESC")
    fun getItemsByProduct(productId: Long): Flow<List<ItemWithProductAndSupplier>>

    @Transaction
    @Query("SELECT * FROM items WHERE supplierId = :supplierId ORDER BY purchaseDate DESC")
    fun getItemsBySupplier(supplierId: Long): Flow<List<ItemWithProductAndSupplier>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertItem(item: ItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>): List<Long>

    @Update suspend fun updateItem(item: ItemEntity)

    @Delete suspend fun deleteItem(item: ItemEntity)

    @Query("DELETE FROM items WHERE id = :id") suspend fun deleteItemById(id: Long)

    @Query("DELETE FROM items") suspend fun deleteAllItems()
}
