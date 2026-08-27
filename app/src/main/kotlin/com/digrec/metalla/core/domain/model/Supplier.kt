package com.digrec.metalla.core.domain.model

import java.util.Date


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
