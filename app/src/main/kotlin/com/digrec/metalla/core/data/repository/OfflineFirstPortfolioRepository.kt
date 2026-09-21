package com.digrec.metalla.core.data.repository

import com.digrec.metalla.core.data.local.dao.ItemDao
import com.digrec.metalla.core.data.local.dao.ProductDao
import com.digrec.metalla.core.data.local.dao.SupplierDao
import com.digrec.metalla.core.data.mapper.asDomainModel
import com.digrec.metalla.core.data.mapper.asEntity
import com.digrec.metalla.core.domain.DispatcherProvider
import com.digrec.metalla.core.domain.MetallaResult
import com.digrec.metalla.core.domain.model.Item
import com.digrec.metalla.core.domain.model.Product
import com.digrec.metalla.core.domain.model.Supplier
import com.digrec.metalla.core.domain.repository.PortfolioRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber

/** Offline-first implementation of [PortfolioRepository] backed by Room DAOs. */
class OfflineFirstPortfolioRepository(
    private val productDao: ProductDao,
    private val supplierDao: SupplierDao,
    private val itemDao: ItemDao,
    private val dispatcherProvider: DispatcherProvider,
) : PortfolioRepository {

    // --- Products ---

    override fun getProducts(): Flow<MetallaResult<List<Product>>> =
        productDao
            .getProducts()
            .map<_, MetallaResult<List<Product>>> { entities ->
                MetallaResult.Success(entities.map { it.asDomainModel() })
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override fun getProductById(id: Long): Flow<MetallaResult<Product?>> =
        productDao
            .getProductById(id)
            .map<_, MetallaResult<Product?>> { entity ->
                MetallaResult.Success(entity?.asDomainModel())
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override suspend fun insertProduct(product: Product): MetallaResult<Long> = safeCall {
        productDao.insertProduct(product.asEntity())
    }

    override suspend fun updateProduct(product: Product): MetallaResult<Unit> = safeCall {
        productDao.updateProduct(product.asEntity())
    }

    override suspend fun deleteProduct(product: Product): MetallaResult<Unit> = safeCall {
        productDao.deleteProduct(product.asEntity())
    }

    override suspend fun deleteProductById(id: Long): MetallaResult<Unit> = safeCall {
        productDao.deleteProductById(id)
    }

    // --- Suppliers ---

    override fun getSuppliers(): Flow<MetallaResult<List<Supplier>>> =
        supplierDao
            .getSuppliers()
            .map<_, MetallaResult<List<Supplier>>> { entities ->
                MetallaResult.Success(entities.map { it.asDomainModel() })
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override fun getSupplierById(id: Long): Flow<MetallaResult<Supplier?>> =
        supplierDao
            .getSupplierById(id)
            .map<_, MetallaResult<Supplier?>> { entity ->
                MetallaResult.Success(entity?.asDomainModel())
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override suspend fun insertSupplier(supplier: Supplier): MetallaResult<Long> = safeCall {
        supplierDao.insertSupplier(supplier.asEntity())
    }

    override suspend fun updateSupplier(supplier: Supplier): MetallaResult<Unit> = safeCall {
        supplierDao.updateSupplier(supplier.asEntity())
    }

    override suspend fun deleteSupplier(supplier: Supplier): MetallaResult<Unit> = safeCall {
        supplierDao.deleteSupplier(supplier.asEntity())
    }

    override suspend fun deleteSupplierById(id: Long): MetallaResult<Unit> = safeCall {
        supplierDao.deleteSupplierById(id)
    }

    // --- Items / Holdings ---

    override fun getItems(): Flow<MetallaResult<List<Item>>> =
        itemDao
            .getItemsWithDetails()
            .map<_, MetallaResult<List<Item>>> { entities ->
                MetallaResult.Success(entities.map { it.asDomainModel() })
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override fun getItemById(id: Long): Flow<MetallaResult<Item?>> =
        itemDao
            .getItemWithDetailsById(id)
            .map<_, MetallaResult<Item?>> { entity ->
                MetallaResult.Success(entity?.asDomainModel())
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override fun getItemsByProduct(productId: Long): Flow<MetallaResult<List<Item>>> =
        itemDao
            .getItemsByProduct(productId)
            .map<_, MetallaResult<List<Item>>> { entities ->
                MetallaResult.Success(entities.map { it.asDomainModel() })
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override fun getItemsBySupplier(supplierId: Long): Flow<MetallaResult<List<Item>>> =
        itemDao
            .getItemsBySupplier(supplierId)
            .map<_, MetallaResult<List<Item>>> { entities ->
                MetallaResult.Success(entities.map { it.asDomainModel() })
            }
            .catch { emit(MetallaResult.Error(it)) }
            .flowOn(dispatcherProvider.io)

    override suspend fun insertItem(item: Item): MetallaResult<Long> = safeCall {
        itemDao.insertItem(item.asEntity())
    }

    override suspend fun updateItem(item: Item): MetallaResult<Unit> = safeCall {
        itemDao.updateItem(item.asEntity())
    }

    override suspend fun deleteItem(item: Item): MetallaResult<Unit> = safeCall {
        itemDao.deleteItem(item.asEntity())
    }

    override suspend fun deleteItemById(id: Long): MetallaResult<Unit> = safeCall {
        itemDao.deleteItemById(id)
    }

    private suspend inline fun <T> safeCall(crossinline action: suspend () -> T): MetallaResult<T> =
        withContext(dispatcherProvider.io) {
            try {
                MetallaResult.Success(action())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e, "Repository operation failed")
                MetallaResult.Error(e)
            }
        }
}
