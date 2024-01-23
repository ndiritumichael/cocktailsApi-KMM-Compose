package di

import data.database.preferences.SettingWrapper
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sourceSetModules: Module = module {
    single { 
        SettingWrapper().createSettings()
    }
}
