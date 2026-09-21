package com.digrec.metalla.core.data.mapper

import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.model.ItemWithProductAndSupplier
import com.digrec.metalla.core.domain.model.Item

fun ItemWithProductAndSupplier.asDomainModel(): Item =
    Item(
        id = item.id,
        product = product.asDomainModel(),
        supplier = supplier.asDomainModel(),
        purchaseDate = item.purchaseDate,
        price = item.price,
        currency = item.currency,
        quantity = item.quantity,
        creationDate = item.creationDate,
    )

fun Item.asEntity(): ItemEntity =
    ItemEntity(
        id = id,
        productId = product.id,
        supplierId = supplier.id,
        purchaseDate = purchaseDate,
        price = price,
        currency = currency,
        quantity = quantity,
        creationDate = creationDate,
    )
