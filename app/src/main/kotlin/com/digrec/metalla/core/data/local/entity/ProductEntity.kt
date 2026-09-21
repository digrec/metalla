package com.digrec.metalla.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.digrec.metalla.core.domain.model.MetalType
import com.digrec.metalla.core.domain.model.ProductForm
import com.digrec.metalla.core.domain.model.WeightUnit
import kotlin.time.Instant

/** Room entity representing a precious metal product. */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val metal: MetalType,
    val form: ProductForm,
    val description: String,
    val weight: Double,
    val weightUnit: WeightUnit,
    val creationDate: Instant,
)
