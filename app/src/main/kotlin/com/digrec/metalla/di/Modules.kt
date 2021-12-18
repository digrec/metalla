package com.digrec.metalla.di

import com.digrec.metalla.ui.dashboard.DashboardViewModel
import com.digrec.metalla.ui.home.HomeViewModel
import com.digrec.metalla.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * All modules used in dependency injection.
 *
 * Created by Dejan Igrec
 */
object Modules {

    val viewModelModule = module {
        viewModel { HomeViewModel() }
        viewModel { DashboardViewModel() }
        viewModel { SettingsViewModel() }
    }
}
