package com.digrec.metalla.core.domain.model

import java.math.BigDecimal
import java.math.MathContext

/**
 * Units of measure for precious metals with conversion utilities.
 *
 * 1 Troy Ounce (ozt) = 31.1034768 grams. 1 Kilogram (kg) = 1000 grams ≈ 32.1507 troy ounces. See
 * docs/GLOSSARY.md.
 */
private const val TROY_OUNCE_IN_GRAMS: Double = 31.1034768

enum class WeightUnit(
    val symbol: String,
    val gramsPerUnit: Double,
) {
    GRAM("g", gramsPerUnit = 1.0),
    TROY_OUNCE("oz t", gramsPerUnit = TROY_OUNCE_IN_GRAMS),
    KILOGRAM("kg", gramsPerUnit = 1000.0);

    /** Converts an amount in this unit to grams. */
    fun toGrams(amount: Double): Double = amount * gramsPerUnit

    /** Converts an amount in grams to this unit. */
    fun fromGrams(amountInGrams: Double): Double = amountInGrams / gramsPerUnit

    /** Converts an amount in this unit to troy ounces. */
    fun toTroyOunces(amount: Double): Double = toGrams(amount) / GRAMS_PER_TROY_OUNCE

    /** Converts an amount in troy ounces to this unit. */
    fun fromTroyOunces(amountInTroyOunces: Double): Double =
        fromGrams(amountInTroyOunces * GRAMS_PER_TROY_OUNCE)

    /** Converts an amount in this unit to the target unit. */
    fun convertTo(amount: Double, targetUnit: WeightUnit): Double =
        targetUnit.fromGrams(toGrams(amount))

    /** Converts a [BigDecimal] amount in this unit to grams. */
    fun toGrams(amount: BigDecimal): BigDecimal = amount.multiply(BigDecimal.valueOf(gramsPerUnit))

    /** Converts a [BigDecimal] amount in grams to this unit. */
    fun fromGrams(
        amountInGrams: BigDecimal,
        mathContext: MathContext = MathContext.DECIMAL128,
    ): BigDecimal = amountInGrams.divide(BigDecimal.valueOf(gramsPerUnit), mathContext)

    /** Converts a [BigDecimal] amount in this unit to troy ounces. */
    fun toTroyOunces(
        amount: BigDecimal,
        mathContext: MathContext = MathContext.DECIMAL128,
    ): BigDecimal = toGrams(amount).divide(BIG_DECIMAL_GRAMS_PER_TROY_OUNCE, mathContext)

    /** Converts a [BigDecimal] amount in troy ounces to this unit. */
    fun fromTroyOunces(amountInTroyOunces: BigDecimal): BigDecimal =
        fromGrams(amountInTroyOunces.multiply(BIG_DECIMAL_GRAMS_PER_TROY_OUNCE))

    /** Converts a [BigDecimal] amount in this unit to the target unit. */
    fun convertTo(
        amount: BigDecimal,
        targetUnit: WeightUnit,
        mathContext: MathContext = MathContext.DECIMAL128,
    ): BigDecimal = targetUnit.fromGrams(toGrams(amount), mathContext)

    companion object {
        const val GRAMS_PER_TROY_OUNCE: Double = 31.1034768
        val BIG_DECIMAL_GRAMS_PER_TROY_OUNCE: BigDecimal = BigDecimal.valueOf(GRAMS_PER_TROY_OUNCE)
    }
}
