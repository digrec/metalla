package com.digrec.metalla.domain.model

import java.util.*


/**
 * Purchase item.
 *
 * Created by Dejan Igrec
 */
data class Item(
    val id: Long,
    val product: Product,
    val supplier: Supplier,
    val purchaseDate: Date,
    val price: Double,
    val currency: String,
    val quantity: Int,
    val creationDate: Date,
)
