package com.digrec.metalla.core.data.repository

import app.cash.turbine.test
import com.digrec.metalla.core.data.local.dao.ItemDao
import com.digrec.metalla.core.data.local.dao.ProductDao
import com.digrec.metalla.core.data.local.dao.SupplierDao
import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.entity.ProductEntity
import com.digrec.metalla.core.data.local.entity.SupplierEntity
import com.digrec.metalla.core.data.local.model.ItemWithProductAndSupplier
import com.digrec.metalla.core.data.mapper.asDomainModel
import com.digrec.metalla.core.data.mapper.asEntity
import com.digrec.metalla.core.domain.MetallaResult
import com.digrec.metalla.core.domain.model.Item
import com.digrec.metalla.core.domain.model.MetalType
import com.digrec.metalla.core.domain.model.Product
import com.digrec.metalla.core.domain.model.ProductForm
import com.digrec.metalla.core.domain.model.Supplier
import com.digrec.metalla.core.domain.model.WeightUnit
import com.digrec.metalla.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import java.math.BigDecimal
import kotlin.time.Instant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OfflineFirstPortfolioRepositoryTest {

    @get:Rule val mainDispatcherRule = MainDispatcherRule()

    private lateinit var productDao: ProductDao
    private lateinit var supplierDao: SupplierDao
    private lateinit var itemDao: ItemDao
    private lateinit var repository: OfflineFirstPortfolioRepository

    private val now = Instant.fromEpochMilliseconds(1726340000000L)

    private val sampleProductEntity =
        ProductEntity(
            id = 1L,
            name = "American Gold Eagle 1 oz",
            metal = MetalType.GOLD,
            form = ProductForm.COIN,
            description = "1 oz Gold Coin",
            weight = 1.0,
            weightUnit = WeightUnit.TROY_OUNCE,
            creationDate = now,
        )

    private val sampleProduct: Product = sampleProductEntity.asDomainModel()

    private val sampleSupplierEntity =
        SupplierEntity(
            id = 10L,
            companyName = "Apex Precious Metals",
            contactName = "John Doe",
            address = "123 Gold St",
            phone = "555-0100",
            mobilePhone = "555-0101",
            emailAddress = "contact@apex.com",
            creationDate = now,
        )

    private val sampleSupplier: Supplier = sampleSupplierEntity.asDomainModel()

    private val sampleItemEntity =
        ItemEntity(
            id = 100L,
            productId = 1L,
            supplierId = 10L,
            purchaseDate = now,
            price = BigDecimal("2500.00"),
            currency = "USD",
            quantity = 1,
            creationDate = now,
        )

    private val sampleItemRelation =
        ItemWithProductAndSupplier(
            item = sampleItemEntity,
            product = sampleProductEntity,
            supplier = sampleSupplierEntity,
        )

    private val sampleItem: Item = sampleItemRelation.asDomainModel()

    @Before
    fun setUp() {
        productDao = mockk()
        supplierDao = mockk()
        itemDao = mockk()
        repository =
            OfflineFirstPortfolioRepository(
                productDao = productDao,
                supplierDao = supplierDao,
                itemDao = itemDao,
                dispatcherProvider = mainDispatcherRule.dispatcherProvider,
            )
    }

    // --- Product Tests ---

    @Test
    fun `getProducts emits Success with mapped domain products`() = runTest {
        every { productDao.getProducts() } returns flowOf(listOf(sampleProductEntity))

        repository.getProducts().test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(listOf(sampleProduct), (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `getProducts emits Error when DAO flow fails`() = runTest {
        val exception = IOException("DB error")
        every { productDao.getProducts() } returns flow { throw exception }

        repository.getProducts().test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Error)
            assertEquals(exception, (item as MetallaResult.Error).exception)
            awaitComplete()
        }
    }

    @Test
    fun `getProductById emits Success with product when found`() = runTest {
        every { productDao.getProductById(1L) } returns flowOf(sampleProductEntity)

        repository.getProductById(1L).test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(sampleProduct, (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `getProductById emits Success with null when not found`() = runTest {
        every { productDao.getProductById(99L) } returns flowOf(null)

        repository.getProductById(99L).test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertNull((item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `insertProduct returns Success with generated ID`() = runTest {
        coEvery { productDao.insertProduct(sampleProduct.asEntity()) } returns 1L

        val result = repository.insertProduct(sampleProduct)

        assertTrue(result is MetallaResult.Success)
        assertEquals(1L, (result as MetallaResult.Success).data)
        coVerify(exactly = 1) { productDao.insertProduct(sampleProduct.asEntity()) }
    }

    @Test
    fun `insertProduct returns Error on exception`() = runTest {
        val exception = RuntimeException("Insert failed")
        coEvery { productDao.insertProduct(any()) } throws exception

        val result = repository.insertProduct(sampleProduct)

        assertTrue(result is MetallaResult.Error)
        assertEquals(exception, (result as MetallaResult.Error).exception)
    }

    @Test
    fun `updateProduct returns Success on completion`() = runTest {
        coEvery { productDao.updateProduct(sampleProduct.asEntity()) } returns Unit

        val result = repository.updateProduct(sampleProduct)

        assertTrue(result is MetallaResult.Success)
        coVerify(exactly = 1) { productDao.updateProduct(sampleProduct.asEntity()) }
    }

    @Test
    fun `deleteProduct returns Success on completion`() = runTest {
        coEvery { productDao.deleteProduct(sampleProduct.asEntity()) } returns Unit

        val result = repository.deleteProduct(sampleProduct)

        assertTrue(result is MetallaResult.Success)
        coVerify(exactly = 1) { productDao.deleteProduct(sampleProduct.asEntity()) }
    }

    @Test
    fun `deleteProductById returns Success on completion`() = runTest {
        coEvery { productDao.deleteProductById(1L) } returns Unit

        val result = repository.deleteProductById(1L)

        assertTrue(result is MetallaResult.Success)
        coVerify(exactly = 1) { productDao.deleteProductById(1L) }
    }

    // --- Supplier Tests ---

    @Test
    fun `getSuppliers emits Success with mapped suppliers`() = runTest {
        every { supplierDao.getSuppliers() } returns flowOf(listOf(sampleSupplierEntity))

        repository.getSuppliers().test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(listOf(sampleSupplier), (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `getSuppliers emits Error when DAO flow fails`() = runTest {
        val exception = IOException("Disk error")
        every { supplierDao.getSuppliers() } returns flow { throw exception }

        repository.getSuppliers().test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Error)
            assertEquals(exception, (item as MetallaResult.Error).exception)
            awaitComplete()
        }
    }

    @Test
    fun `getSupplierById emits Success with supplier`() = runTest {
        every { supplierDao.getSupplierById(10L) } returns flowOf(sampleSupplierEntity)

        repository.getSupplierById(10L).test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(sampleSupplier, (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `insertSupplier returns Success with generated ID`() = runTest {
        coEvery { supplierDao.insertSupplier(sampleSupplier.asEntity()) } returns 10L

        val result = repository.insertSupplier(sampleSupplier)

        assertTrue(result is MetallaResult.Success)
        assertEquals(10L, (result as MetallaResult.Success).data)
    }

    @Test
    fun `updateSupplier returns Success on completion`() = runTest {
        coEvery { supplierDao.updateSupplier(sampleSupplier.asEntity()) } returns Unit

        val result = repository.updateSupplier(sampleSupplier)

        assertTrue(result is MetallaResult.Success)
    }

    @Test
    fun `deleteSupplier returns Success on completion`() = runTest {
        coEvery { supplierDao.deleteSupplier(sampleSupplier.asEntity()) } returns Unit

        val result = repository.deleteSupplier(sampleSupplier)

        assertTrue(result is MetallaResult.Success)
    }

    @Test
    fun `deleteSupplierById returns Success on completion`() = runTest {
        coEvery { supplierDao.deleteSupplierById(10L) } returns Unit

        val result = repository.deleteSupplierById(10L)

        assertTrue(result is MetallaResult.Success)
    }

    // --- Item Tests ---

    @Test
    fun `getItems emits Success with composite items`() = runTest {
        every { itemDao.getItemsWithDetails() } returns flowOf(listOf(sampleItemRelation))

        repository.getItems().test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(listOf(sampleItem), (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `getItems emits Error when DAO flow fails`() = runTest {
        val exception = IOException("Read error")
        every { itemDao.getItemsWithDetails() } returns flow { throw exception }

        repository.getItems().test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Error)
            assertEquals(exception, (item as MetallaResult.Error).exception)
            awaitComplete()
        }
    }

    @Test
    fun `getItemById emits Success with item`() = runTest {
        every { itemDao.getItemWithDetailsById(100L) } returns flowOf(sampleItemRelation)

        repository.getItemById(100L).test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(sampleItem, (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `getItemsByProduct emits Success with matching items`() = runTest {
        every { itemDao.getItemsByProduct(1L) } returns flowOf(listOf(sampleItemRelation))

        repository.getItemsByProduct(1L).test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(listOf(sampleItem), (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `getItemsBySupplier emits Success with matching items`() = runTest {
        every { itemDao.getItemsBySupplier(10L) } returns flowOf(listOf(sampleItemRelation))

        repository.getItemsBySupplier(10L).test {
            val item = awaitItem()
            assertTrue(item is MetallaResult.Success)
            assertEquals(listOf(sampleItem), (item as MetallaResult.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `insertItem returns Success with generated ID`() = runTest {
        coEvery { itemDao.insertItem(sampleItem.asEntity()) } returns 100L

        val result = repository.insertItem(sampleItem)

        assertTrue(result is MetallaResult.Success)
        assertEquals(100L, (result as MetallaResult.Success).data)
    }

    @Test
    fun `updateItem returns Success on completion`() = runTest {
        coEvery { itemDao.updateItem(sampleItem.asEntity()) } returns Unit

        val result = repository.updateItem(sampleItem)

        assertTrue(result is MetallaResult.Success)
    }

    @Test
    fun `deleteItem returns Success on completion`() = runTest {
        coEvery { itemDao.deleteItem(sampleItem.asEntity()) } returns Unit

        val result = repository.deleteItem(sampleItem)

        assertTrue(result is MetallaResult.Success)
    }

    @Test
    fun `deleteItemById returns Success on completion`() = runTest {
        coEvery { itemDao.deleteItemById(100L) } returns Unit

        val result = repository.deleteItemById(100L)

        assertTrue(result is MetallaResult.Success)
    }
}
