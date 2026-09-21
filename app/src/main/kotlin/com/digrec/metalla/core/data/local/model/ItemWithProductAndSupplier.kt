package com.digrec.metalla.core.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.entity.ProductEntity
import com.digrec.metalla.core.data.local.entity.SupplierEntity

/**
 * Composite model representing an [ItemEntity] joined with its related [ProductEntity] and
 * [SupplierEntity].
 */
data class ItemWithProductAndSupplier(
    @Embedded val item: ItemEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity,
    @Relation(
        parentColumn = "supplierId",
        entityColumn = "id",
    )
    val supplier: SupplierEntity,
)
