package com.digrec.metalla.core.domain.model

import kotlin.time.Instant

/**
 * Precious metal product.
 *
 * Created by Dejan Igrec
 */
data class Product(
    val id: Long,
    val name: String,
    val metal: MetalType,
    val form: ProductForm,
    val description: String,
    val weight: Double,
    val weightUnit: WeightUnit = WeightUnit.TROY_OUNCE,
    val creationDate: Instant,
)
