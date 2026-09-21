package com.digrec.metalla.core.data.mapper

import com.digrec.metalla.core.data.local.entity.SupplierEntity
import com.digrec.metalla.core.domain.model.Supplier

fun SupplierEntity.asDomainModel(): Supplier =
    Supplier(
        id = id,
        companyName = companyName,
        contactName = contactName,
        address = address,
        phone = phone,
        mobilePhone = mobilePhone,
        emailAddress = emailAddress,
        creationDate = creationDate,
    )

fun Supplier.asEntity(): SupplierEntity =
    SupplierEntity(
        id = id,
        companyName = companyName,
        contactName = contactName,
        address = address,
        phone = phone,
        mobilePhone = mobilePhone,
        emailAddress = emailAddress,
        creationDate = creationDate,
    )
