package com.digrec.metalla.core.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digrec.metalla.core.data.local.dao.ItemDao
import com.digrec.metalla.core.data.local.dao.ProductDao
import com.digrec.metalla.core.data.local.dao.SupplierDao
import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.entity.ProductEntity
import com.digrec.metalla.core.data.local.entity.SupplierEntity
import com.digrec.metalla.core.domain.model.MetalType
import com.digrec.metalla.core.domain.model.ProductForm
import com.digrec.metalla.core.domain.model.WeightUnit
import java.math.BigDecimal
import kotlin.time.Instant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MetallaDatabaseTest {

    private lateinit var db: MetallaDatabase
    private lateinit var productDao: ProductDao
    private lateinit var supplierDao: SupplierDao
    private lateinit var itemDao: ItemDao

    private val now = Instant.fromEpochMilliseconds(1726340000000L)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room.inMemoryDatabaseBuilder(context, MetallaDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        productDao = db.productDao()
        supplierDao = db.supplierDao()
        itemDao = db.itemDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun productDao_crudOperationsAndFlow() = runBlocking {
        val product =
            ProductEntity(
                id = 1L,
                name = "Krugerrand 1 oz",
                metal = MetalType.GOLD,
                form = ProductForm.COIN,
                description = "South African Krugerrand",
                weight = 1.0,
                weightUnit = WeightUnit.TROY_OUNCE,
                creationDate = now,
            )

        val insertedId = productDao.insertProduct(product)
        assertEquals(1L, insertedId)

        val list = productDao.getProducts().first()
        assertEquals(1, list.size)
        assertEquals("Krugerrand 1 oz", list[0].name)
        assertEquals(MetalType.GOLD, list[0].metal)

        val item = productDao.getProductById(1L).first()
        assertNotNull(item)
        assertEquals(ProductForm.COIN, item!!.form)

        // Update
        val updatedProduct = product.copy(description = "Updated description")
        productDao.updateProduct(updatedProduct)

        val retrieved = productDao.getProductByIdSync(1L)
        assertNotNull(retrieved)
        assertEquals("Updated description", retrieved!!.description)

        // Delete
        productDao.deleteProductById(1L)
        assertNull(productDao.getProductByIdSync(1L))
    }

    @Test
    fun supplierDao_crudOperationsAndFlow() = runBlocking {
        val supplier =
            SupplierEntity(
                id = 10L,
                companyName = "GoldDirect",
                contactName = "Jane Smith",
                address = "456 Bullion Way",
                phone = "555-0200",
                mobilePhone = "555-0201",
                emailAddress = "info@golddirect.com",
                creationDate = now,
            )

        val insertedId = supplierDao.insertSupplier(supplier)
        assertEquals(10L, insertedId)

        val list = supplierDao.getSuppliers().first()
        assertEquals(1, list.size)
        assertEquals("GoldDirect", list[0].companyName)

        val item = supplierDao.getSupplierById(10L).first()
        assertNotNull(item)
        assertEquals("Jane Smith", item!!.contactName)

        // Update
        val updated = supplier.copy(phone = "555-9999")
        supplierDao.updateSupplier(updated)
        val retrieved = supplierDao.getSupplierByIdSync(10L)
        assertNotNull(retrieved)
        assertEquals("555-9999", retrieved!!.phone)

        // Delete
        supplierDao.deleteSupplierById(10L)
        assertNull(supplierDao.getSupplierByIdSync(10L))
    }

    @Test
    fun itemDao_foreignKeyRelationsAndCompositeQueries() = runBlocking {
        val product =
            ProductEntity(
                id = 1L,
                name = "Silver Maple Leaf 1 oz",
                metal = MetalType.SILVER,
                form = ProductForm.COIN,
                description = "Royal Canadian Mint Silver",
                weight = 1.0,
                weightUnit = WeightUnit.TROY_OUNCE,
                creationDate = now,
            )
        val supplier =
            SupplierEntity(
                id = 2L,
                companyName = "SilverCo",
                contactName = "Bob Ross",
                address = "789 Paint St",
                phone = "555-0300",
                mobilePhone = "555-0301",
                emailAddress = "bob@silverco.com",
                creationDate = now,
            )

        productDao.insertProduct(product)
        supplierDao.insertSupplier(supplier)

        val item =
            ItemEntity(
                id = 100L,
                productId = 1L,
                supplierId = 2L,
                purchaseDate = now,
                price = BigDecimal("32.50"),
                currency = "USD",
                quantity = 5,
                creationDate = now,
            )

        itemDao.insertItem(item)

        val list = itemDao.getItemsWithDetails().first()
        assertEquals(1, list.size)
        val detailed = list[0]
        assertEquals(100L, detailed.item.id)
        assertEquals("Silver Maple Leaf 1 oz", detailed.product.name)
        assertEquals("SilverCo", detailed.supplier.companyName)
        assertEquals(BigDecimal("32.50"), detailed.item.price)
        assertEquals(5, detailed.item.quantity)

        val detailedItem = itemDao.getItemWithDetailsById(100L).first()
        assertNotNull(detailedItem)
        assertEquals(MetalType.SILVER, detailedItem!!.product.metal)
        assertEquals("Bob Ross", detailedItem.supplier.contactName)

        val byProduct = itemDao.getItemsByProduct(1L).first()
        assertEquals(1, byProduct.size)

        val bySupplier = itemDao.getItemsBySupplier(2L).first()
        assertEquals(1, bySupplier.size)

        // Update
        val updatedItem = item.copy(quantity = 10)
        itemDao.updateItem(updatedItem)
        val retrieved = itemDao.getItemWithDetailsByIdSync(100L)
        assertNotNull(retrieved)
        assertEquals(10, retrieved!!.item.quantity)

        // Delete
        itemDao.deleteItemById(100L)
        assertNull(itemDao.getItemWithDetailsByIdSync(100L))
    }
}
