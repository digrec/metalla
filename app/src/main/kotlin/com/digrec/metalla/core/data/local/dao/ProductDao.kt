package com.digrec.metalla.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digrec.metalla.core.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

/** Data Access Object for the [ProductEntity] table. */
@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY name ASC") fun getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProductById(id: Long): Flow<ProductEntity?>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductByIdSync(id: Long): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>): List<Long>

    @Update suspend fun updateProduct(product: ProductEntity)

    @Delete suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE id = :id") suspend fun deleteProductById(id: Long)

    @Query("DELETE FROM products") suspend fun deleteAllProducts()
}
