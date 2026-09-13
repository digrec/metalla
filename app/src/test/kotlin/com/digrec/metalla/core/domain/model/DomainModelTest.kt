package com.digrec.metalla.core.domain.model

import java.math.BigDecimal
import kotlin.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DomainModelTest {

    @Test
    fun `product can be instantiated with pure domain types`() {
        val now = Instant.parse("2026-09-13T12:00:00Z")
        val product =
            Product(
                id = 1L,
                name = "American Gold Eagle 1 oz",
                metal = MetalType.GOLD,
                form = ProductForm.COIN,
                description = "1 oz Gold American Eagle Coin",
                weight = 1.0,
                weightUnit = WeightUnit.TROY_OUNCE,
                creationDate = now,
            )

        assertEquals(1L, product.id)
        assertEquals("American Gold Eagle 1 oz", product.name)
        assertEquals(MetalType.GOLD, product.metal)
        assertEquals("Au", product.metal.symbol)
        assertEquals(ProductForm.COIN, product.form)
        assertEquals(1.0, product.weight, 0.0)
        assertEquals(WeightUnit.TROY_OUNCE, product.weightUnit)
        assertEquals(now, product.creationDate)
    }

    @Test
    fun `supplier can be instantiated with instant creationDate`() {
        val now = Instant.parse("2026-09-13T12:00:00Z")
        val supplier =
            Supplier(
                id = 1L,
                companyName = "Apex Precious Metals",
                contactName = "John Doe",
                address = "123 Bullion Way",
                phone = "+1234567890",
                mobilePhone = "+1987654321",
                emailAddress = "contact@apexmetals.com",
                creationDate = now,
            )

        assertEquals(1L, supplier.id)
        assertEquals("Apex Precious Metals", supplier.companyName)
        assertEquals(now, supplier.creationDate)
    }

    @Test
    fun `item can be instantiated with big decimal price and instant dates`() {
        val creationInstant = Instant.parse("2026-09-13T12:00:00Z")
        val purchaseInstant = Instant.parse("2026-09-10T09:30:00Z")
        val product =
            Product(
                id = 1L,
                name = "Silver Philharmonic 1 oz",
                metal = MetalType.SILVER,
                form = ProductForm.COIN,
                description = "1 oz Silver Vienna Philharmonic",
                weight = 1.0,
                creationDate = creationInstant,
            )
        val supplier =
            Supplier(
                id = 1L,
                companyName = "Austrian Mint",
                contactName = "Sales",
                address = "Am Heumarkt 1, 1030 Wien",
                phone = "+43 1 717150",
                mobilePhone = "",
                emailAddress = "info@muenzeoesterreich.at",
                creationDate = creationInstant,
            )
        val item =
            Item(
                id = 100L,
                product = product,
                supplier = supplier,
                purchaseDate = purchaseInstant,
                price = BigDecimal("32.50"),
                currency = "EUR",
                quantity = 10,
                creationDate = creationInstant,
            )

        assertEquals(100L, item.id)
        assertEquals(product, item.product)
        assertEquals(supplier, item.supplier)
        assertEquals(purchaseInstant, item.purchaseDate)
        assertEquals(BigDecimal("32.50"), item.price)
        assertEquals("EUR", item.currency)
        assertEquals(10, item.quantity)
        assertEquals(creationInstant, item.creationDate)
    }

    @Test
    fun `metal types define correct elemental symbols`() {
        assertEquals("Au", MetalType.GOLD.symbol)
        assertEquals("Ag", MetalType.SILVER.symbol)
        assertEquals("Pt", MetalType.PLATINUM.symbol)
        assertEquals("Pd", MetalType.PALLADIUM.symbol)
    }

    @Test
    fun `product forms include all required physical forms`() {
        assertNotNull(ProductForm.valueOf("BAR"))
        assertNotNull(ProductForm.valueOf("COIN"))
        assertNotNull(ProductForm.valueOf("ROUND"))
        assertNotNull(ProductForm.valueOf("GRAIN"))
    }
}
