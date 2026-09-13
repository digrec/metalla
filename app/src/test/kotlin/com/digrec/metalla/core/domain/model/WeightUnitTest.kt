package com.digrec.metalla.core.domain.model

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeightUnitTest {

    @Test
    fun `troy ounce to grams conversion matches standard constant`() {
        val grams = WeightUnit.TROY_OUNCE.toGrams(1.0)
        assertEquals(31.1034768, grams, 0.0000001)
    }

    @Test
    fun `grams to troy ounce conversion is accurate`() {
        val troyOunces = WeightUnit.GRAM.toTroyOunces(31.1034768)
        assertEquals(1.0, troyOunces, 0.0000001)
    }

    @Test
    fun `kilogram to grams conversion is 1000`() {
        val grams = WeightUnit.KILOGRAM.toGrams(1.0)
        assertEquals(1000.0, grams, 0.0000001)
    }

    @Test
    fun `kilogram to troy ounces matches glossary approximation of 32_1507`() {
        val troyOunces = WeightUnit.KILOGRAM.toTroyOunces(1.0)
        assertEquals(32.1507, troyOunces, 0.0001)
    }

    @Test
    fun `convertTo converts between any units correctly`() {
        // 1 kg = 1000 g
        assertEquals(1000.0, WeightUnit.KILOGRAM.convertTo(1.0, WeightUnit.GRAM), 0.0000001)
        // 1000 g = 1 kg
        assertEquals(1.0, WeightUnit.GRAM.convertTo(1000.0, WeightUnit.KILOGRAM), 0.0000001)
        // 1 ozt = 31.1034768 g
        assertEquals(31.1034768, WeightUnit.TROY_OUNCE.convertTo(1.0, WeightUnit.GRAM), 0.0000001)
        // 31.1034768 g = 1 ozt
        assertEquals(1.0, WeightUnit.GRAM.convertTo(31.1034768, WeightUnit.TROY_OUNCE), 0.0000001)
        // Identity conversion
        assertEquals(5.0, WeightUnit.TROY_OUNCE.convertTo(5.0, WeightUnit.TROY_OUNCE), 0.0000001)
    }

    @Test
    fun `big decimal conversions maintain precision`() {
        val oneKgInGrams = WeightUnit.KILOGRAM.convertTo(BigDecimal.ONE, WeightUnit.GRAM)
        assertEquals(0, BigDecimal("1000.0").compareTo(oneKgInGrams))

        val oneOztInGrams = WeightUnit.TROY_OUNCE.toGrams(BigDecimal.ONE)
        assertEquals(0, WeightUnit.BIG_DECIMAL_GRAMS_PER_TROY_OUNCE.compareTo(oneOztInGrams))

        val oneOzt = WeightUnit.GRAM.toTroyOunces(WeightUnit.BIG_DECIMAL_GRAMS_PER_TROY_OUNCE)
        assertEquals(0, BigDecimal.ONE.compareTo(oneOzt))

        val kgInOzt = WeightUnit.KILOGRAM.toTroyOunces(BigDecimal.ONE)
        assertTrue(kgInOzt.toDouble() in 32.1507..32.1508)
    }

    @Test
    fun `symbols match precious metal standards`() {
        assertEquals("g", WeightUnit.GRAM.symbol)
        assertEquals("oz t", WeightUnit.TROY_OUNCE.symbol)
        assertEquals("kg", WeightUnit.KILOGRAM.symbol)
    }
}
