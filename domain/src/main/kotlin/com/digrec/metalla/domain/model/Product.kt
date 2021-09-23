package com.digrec.metalla.domain.model

import java.util.Date


/**
 * Precious metal product.
 *
 * Created by Dejan Igrec
 */
data class Product(
    val id: Long,
    val name: String,
    val metal: String,
    val form: String,
    val drawableId: Int,
    val description: String,
    val weight: Float,
    val creationDate: Date,
)
