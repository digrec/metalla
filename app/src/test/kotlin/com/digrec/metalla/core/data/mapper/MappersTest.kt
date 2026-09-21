package com.digrec.metalla.core.data.mapper

import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.entity.ProductEntity
import com.digrec.metalla.core.data.local.entity.SupplierEntity
import com.digrec.metalla.core.data.local.model.ItemWithProductAndSupplier
import com.digrec.metalla.core.domain.model.Item
import com.digrec.metalla.core.domain.model.MetalType
import com.digrec.metalla.core.domain.model.Product
import com.digrec.metalla.core.domain.model.ProductForm
import com.digrec.metalla.core.domain.model.Supplier
import com.digrec.metalla.core.domain.model.WeightUnit
import java.math.BigDecimal
import kotlin.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val now = Instant.fromEpochMilliseconds(1726340000000L)

    private val sampleProductEntity =
        ProductEntity(
            id = 1L,
            name = "American Gold Eagle 1 oz",
            metal = MetalType.GOLD,
            form = ProductForm.COIN,
            description = "Official gold bullion coin of the US",
            weight = 1.0,
            weightUnit = WeightUnit.TROY_OUNCE,
            creationDate = now,
        )

    private val sampleProduct =
        Product(
            id = 1L,
            name = "American Gold Eagle 1 oz",
            metal = MetalType.GOLD,
            form = ProductForm.COIN,
            description = "Official gold bullion coin of the US",
            weight = 1.0,
            weightUnit = WeightUnit.TROY_OUNCE,
            creationDate = now,
        )

    private val sampleSupplierEntity =
        SupplierEntity(
            id = 10L,
            companyName = "Apex Precious Metals",
            contactName = "John Doe",
            address = "123 Gold St, NY",
            phone = "555-0100",
            mobilePhone = "555-0101",
            emailAddress = "orders@apexmetals.com",
            creationDate = now,
        )

    private val sampleSupplier =
        Supplier(
            id = 10L,
            companyName = "Apex Precious Metals",
            contactName = "John Doe",
            address = "123 Gold St, NY",
            phone = "555-0100",
            mobilePhone = "555-0101",
            emailAddress = "orders@apexmetals.com",
            creationDate = now,
        )

    private val sampleItemEntity =
        ItemEntity(
            id = 100L,
            productId = 1L,
            supplierId = 10L,
            purchaseDate = now,
            price = BigDecimal("2450.00"),
            currency = "USD",
            quantity = 2,
            creationDate = now,
        )

    private val sampleItem =
        Item(
            id = 100L,
            product = sampleProduct,
            supplier = sampleSupplier,
            purchaseDate = now,
            price = BigDecimal("2450.00"),
            currency = "USD",
            quantity = 2,
            creationDate = now,
        )

    @Test
    fun `map ProductEntity to Product domain model`() {
        val result = sampleProductEntity.asDomainModel()
        assertEquals(sampleProduct, result)
    }

    @Test
    fun `map Product domain model to ProductEntity`() {
        val result = sampleProduct.asEntity()
        assertEquals(sampleProductEntity, result)
    }

    @Test
    fun `map SupplierEntity to Supplier domain model`() {
        val result = sampleSupplierEntity.asDomainModel()
        assertEquals(sampleSupplier, result)
    }

    @Test
    fun `map Supplier domain model to SupplierEntity`() {
        val result = sampleSupplier.asEntity()
        assertEquals(sampleSupplierEntity, result)
    }

    @Test
    fun `map ItemWithProductAndSupplier to Item domain model`() {
        val relation =
            ItemWithProductAndSupplier(
                item = sampleItemEntity,
                product = sampleProductEntity,
                supplier = sampleSupplierEntity,
            )

        val result = relation.asDomainModel()
        assertEquals(sampleItem, result)
    }

    @Test
    fun `map Item domain model to ItemEntity`() {
        val result = sampleItem.asEntity()
        assertEquals(sampleItemEntity, result)
    }
}
