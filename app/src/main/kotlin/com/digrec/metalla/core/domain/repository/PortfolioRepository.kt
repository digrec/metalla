package com.digrec.metalla.core.domain.repository

import com.digrec.metalla.core.domain.MetallaResult
import com.digrec.metalla.core.domain.model.Item
import com.digrec.metalla.core.domain.model.Product
import com.digrec.metalla.core.domain.model.Supplier
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing the precious metals portfolio.
 *
 * Provides reactive [Flow] streams and suspend operations for [Product]s, [Supplier]s, and [Item]
 * holdings. Uses type-driven error handling ([MetallaResult]) across all APIs.
 */
interface PortfolioRepository {

    // --- Products ---

    fun getProducts(): Flow<MetallaResult<List<Product>>>

    fun getProductById(id: Long): Flow<MetallaResult<Product?>>

    suspend fun insertProduct(product: Product): MetallaResult<Long>

    suspend fun updateProduct(product: Product): MetallaResult<Unit>

    suspend fun deleteProduct(product: Product): MetallaResult<Unit>

    suspend fun deleteProductById(id: Long): MetallaResult<Unit>

    // --- Suppliers ---

    fun getSuppliers(): Flow<MetallaResult<List<Supplier>>>

    fun getSupplierById(id: Long): Flow<MetallaResult<Supplier?>>

    suspend fun insertSupplier(supplier: Supplier): MetallaResult<Long>

    suspend fun updateSupplier(supplier: Supplier): MetallaResult<Unit>

    suspend fun deleteSupplier(supplier: Supplier): MetallaResult<Unit>

    suspend fun deleteSupplierById(id: Long): MetallaResult<Unit>

    // --- Items / Holdings ---

    fun getItems(): Flow<MetallaResult<List<Item>>>

    fun getItemById(id: Long): Flow<MetallaResult<Item?>>

    fun getItemsByProduct(productId: Long): Flow<MetallaResult<List<Item>>>

    fun getItemsBySupplier(supplierId: Long): Flow<MetallaResult<List<Item>>>

    suspend fun insertItem(item: Item): MetallaResult<Long>

    suspend fun updateItem(item: Item): MetallaResult<Unit>

    suspend fun deleteItem(item: Item): MetallaResult<Unit>

    suspend fun deleteItemById(id: Long): MetallaResult<Unit>
}
