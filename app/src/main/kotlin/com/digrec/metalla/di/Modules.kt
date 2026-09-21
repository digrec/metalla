package com.digrec.metalla.di

import androidx.room.Room
import com.digrec.metalla.core.data.local.MetallaDatabase
import com.digrec.metalla.core.data.repository.OfflineFirstPortfolioRepository
import com.digrec.metalla.core.domain.DefaultDispatcherProvider
import com.digrec.metalla.core.domain.DispatcherProvider
import com.digrec.metalla.core.domain.repository.PortfolioRepository
import com.digrec.metalla.features.dashboard.DashboardViewModel
import com.digrec.metalla.features.home.HomeViewModel
import com.digrec.metalla.features.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * All modules used in dependency injection.
 *
 * Created by Dejan Igrec
 */
object Modules {

    val coreModule = module { singleOf(::DefaultDispatcherProvider) bind DispatcherProvider::class }

    val dataModule = module {
        single {
            Room.databaseBuilder(
                    androidContext(),
                    MetallaDatabase::class.java,
                    MetallaDatabase.DATABASE_NAME,
                )
                .build()
        }
        single { get<MetallaDatabase>().productDao() }
        single { get<MetallaDatabase>().supplierDao() }
        single { get<MetallaDatabase>().itemDao() }

        singleOf(::OfflineFirstPortfolioRepository) bind PortfolioRepository::class
    }

    val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::SettingsViewModel)
    }
}
