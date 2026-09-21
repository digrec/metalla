package com.digrec.metalla.core.data.local.converter

import androidx.room.TypeConverter
import com.digrec.metalla.core.domain.model.MetalType
import com.digrec.metalla.core.domain.model.ProductForm
import com.digrec.metalla.core.domain.model.WeightUnit
import java.math.BigDecimal
import kotlin.time.Instant

/**
 * Room type converters for domain types: [Instant], [BigDecimal], [MetalType], [ProductForm], and
 * [WeightUnit].
 */
class Converters {

    @TypeConverter fun fromInstant(value: Instant?): Long? = value?.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let { Instant.fromEpochMilliseconds(it) }

    @TypeConverter fun fromBigDecimal(value: BigDecimal?): String? = value?.toPlainString()

    @TypeConverter fun toBigDecimal(value: String?): BigDecimal? = value?.let { BigDecimal(it) }

    @TypeConverter fun fromMetalType(value: MetalType?): String? = value?.name

    @TypeConverter
    fun toMetalType(value: String?): MetalType? = value?.let { enumValueOf<MetalType>(it) }

    @TypeConverter fun fromProductForm(value: ProductForm?): String? = value?.name

    @TypeConverter
    fun toProductForm(value: String?): ProductForm? = value?.let { enumValueOf<ProductForm>(it) }

    @TypeConverter fun fromWeightUnit(value: WeightUnit?): String? = value?.name

    @TypeConverter
    fun toWeightUnit(value: String?): WeightUnit? = value?.let { enumValueOf<WeightUnit>(it) }
}
