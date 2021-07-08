package com.digrec.metalla.domain.model

import java.util.*


/**
 * Supplier of a [Product].
 *
 * Created by Dejan Igrec
 */
data class Supplier(
    val id: Long,
    val companyName: String,
    val contactName: String,
    val address: String,
    val phone: String,
    val mobilePhone: String,
    val emailAddress: String,
    val creationDate: Date,
)
