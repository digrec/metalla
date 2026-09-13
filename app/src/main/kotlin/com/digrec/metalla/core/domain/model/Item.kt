package com.digrec.metalla.core.domain.model

import java.math.BigDecimal
import kotlin.time.Instant

/**
 * Purchase item.
 *
 * Created by Dejan Igrec
 */
data class Item(
    val id: Long,
    val product: Product,
    val supplier: Supplier,
    val purchaseDate: Instant,
    val price: BigDecimal,
    val currency: String,
    val quantity: Int,
    val creationDate: Instant,
)
