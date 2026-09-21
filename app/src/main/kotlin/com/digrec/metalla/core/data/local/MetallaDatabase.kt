package com.digrec.metalla.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digrec.metalla.core.data.local.converter.Converters
import com.digrec.metalla.core.data.local.dao.ItemDao
import com.digrec.metalla.core.data.local.dao.ProductDao
import com.digrec.metalla.core.data.local.dao.SupplierDao
import com.digrec.metalla.core.data.local.entity.ItemEntity
import com.digrec.metalla.core.data.local.entity.ProductEntity
import com.digrec.metalla.core.data.local.entity.SupplierEntity

/** Room Database for Metalla offline persistence. */
@Database(
    entities =
        [
            ProductEntity::class,
            SupplierEntity::class,
            ItemEntity::class,
        ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class MetallaDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun supplierDao(): SupplierDao

    abstract fun itemDao(): ItemDao

    companion object {
        const val DATABASE_NAME = "metalla.db"
    }
}
