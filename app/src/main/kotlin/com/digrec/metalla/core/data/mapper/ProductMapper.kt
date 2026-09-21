package com.digrec.metalla.core.data.mapper

import com.digrec.metalla.core.data.local.entity.ProductEntity
import com.digrec.metalla.core.domain.model.Product

fun ProductEntity.asDomainModel(): Product =
    Product(
        id = id,
        name = name,
        metal = metal,
        form = form,
        description = description,
        weight = weight,
        weightUnit = weightUnit,
        creationDate = creationDate,
    )

fun Product.asEntity(): ProductEntity =
    ProductEntity(
        id = id,
        name = name,
        metal = metal,
        form = form,
        description = description,
        weight = weight,
        weightUnit = weightUnit,
        creationDate = creationDate,
    )
