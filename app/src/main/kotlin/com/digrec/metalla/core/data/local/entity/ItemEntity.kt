package com.digrec.metalla.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import kotlin.time.Instant

/** Room entity representing a purchased holding/item. */
@Entity(
    tableName = "items",
    foreignKeys =
        [
            ForeignKey(
                entity = ProductEntity::class,
                parentColumns = ["id"],
                childColumns = ["productId"],
                onDelete = ForeignKey.RESTRICT,
            ),
            ForeignKey(
                entity = SupplierEntity::class,
                parentColumns = ["id"],
                childColumns = ["supplierId"],
                onDelete = ForeignKey.RESTRICT,
            ),
        ],
    indices =
        [
            Index(value = ["productId"]),
            Index(value = ["supplierId"]),
        ],
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "productId") val productId: Long,
    @ColumnInfo(name = "supplierId") val supplierId: Long,
    val purchaseDate: Instant,
    val price: BigDecimal,
    val currency: String,
    val quantity: Int,
    val creationDate: Instant,
)
