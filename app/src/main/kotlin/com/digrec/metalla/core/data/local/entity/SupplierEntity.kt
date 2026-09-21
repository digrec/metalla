package com.digrec.metalla.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

/** Room entity representing a product supplier. */
@Entity(tableName = "suppliers")
data class SupplierEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val companyName: String,
    val contactName: String,
    val address: String,
    val phone: String,
    val mobilePhone: String,
    val emailAddress: String,
    val creationDate: Instant,
)
