package com.digrec.metalla.di

import com.digrec.metalla.core.domain.DefaultDispatcherProvider
import com.digrec.metalla.core.domain.DispatcherProvider
import com.digrec.metalla.features.dashboard.DashboardViewModel
import com.digrec.metalla.features.home.HomeViewModel
import com.digrec.metalla.features.settings.SettingsViewModel
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

    val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::SettingsViewModel)
    }
}
