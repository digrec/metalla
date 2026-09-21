package com.digrec.metalla.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digrec.metalla.core.data.local.entity.SupplierEntity
import kotlinx.coroutines.flow.Flow

/** Data Access Object for the [SupplierEntity] table. */
@Dao
interface SupplierDao {

    @Query("SELECT * FROM suppliers ORDER BY companyName ASC")
    fun getSuppliers(): Flow<List<SupplierEntity>>

    @Query("SELECT * FROM suppliers WHERE id = :id")
    fun getSupplierById(id: Long): Flow<SupplierEntity?>

    @Query("SELECT * FROM suppliers WHERE id = :id")
    suspend fun getSupplierByIdSync(id: Long): SupplierEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplier(supplier: SupplierEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuppliers(suppliers: List<SupplierEntity>): List<Long>

    @Update suspend fun updateSupplier(supplier: SupplierEntity)

    @Delete suspend fun deleteSupplier(supplier: SupplierEntity)

    @Query("DELETE FROM suppliers WHERE id = :id") suspend fun deleteSupplierById(id: Long)

    @Query("DELETE FROM suppliers") suspend fun deleteAllSuppliers()
}
