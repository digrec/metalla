package com.digrec.metalla.core.data.local

import com.digrec.metalla.core.data.local.converter.Converters
import com.digrec.metalla.core.domain.model.MetalType
import com.digrec.metalla.core.domain.model.ProductForm
import com.digrec.metalla.core.domain.model.WeightUnit
import java.math.BigDecimal
import kotlin.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setUp() {
        converters = Converters()
    }

    @Test
    fun `convert Instant to epoch millis and back`() {
        val instant = Instant.fromEpochMilliseconds(1726340000000L)

        val millis = converters.fromInstant(instant)
        val result = converters.toInstant(millis)

        assertEquals(1726340000000L, millis)
        assertEquals(instant, result)
    }

    @Test
    fun `convert null Instant returns null`() {
        assertNull(converters.fromInstant(null))
        assertNull(converters.toInstant(null))
    }

    @Test
    fun `convert BigDecimal to plain string and back`() {
        val decimal = BigDecimal("2499.99")

        val stringValue = converters.fromBigDecimal(decimal)
        val result = converters.toBigDecimal(stringValue)

        assertEquals("2499.99", stringValue)
        assertEquals(decimal, result)
    }

    @Test
    fun `convert null BigDecimal returns null`() {
        assertNull(converters.fromBigDecimal(null))
        assertNull(converters.toBigDecimal(null))
    }

    @Test
    fun `convert all MetalType values to string and back`() {
        for (metal in MetalType.entries) {
            val name = converters.fromMetalType(metal)
            val result = converters.toMetalType(name)

            assertEquals(metal.name, name)
            assertEquals(metal, result)
        }
    }

    @Test
    fun `convert null MetalType returns null`() {
        assertNull(converters.fromMetalType(null))
        assertNull(converters.toMetalType(null))
    }

    @Test
    fun `convert all ProductForm values to string and back`() {
        for (form in ProductForm.entries) {
            val name = converters.fromProductForm(form)
            val result = converters.toProductForm(name)

            assertEquals(form.name, name)
            assertEquals(form, result)
        }
    }

    @Test
    fun `convert null ProductForm returns null`() {
        assertNull(converters.fromProductForm(null))
        assertNull(converters.toProductForm(null))
    }

    @Test
    fun `convert all WeightUnit values to string and back`() {
        for (unit in WeightUnit.entries) {
            val name = converters.fromWeightUnit(unit)
            val result = converters.toWeightUnit(name)

            assertEquals(unit.name, name)
            assertEquals(unit, result)
        }
    }

    @Test
    fun `convert null WeightUnit returns null`() {
        assertNull(converters.fromWeightUnit(null))
        assertNull(converters.toWeightUnit(null))
    }
}
