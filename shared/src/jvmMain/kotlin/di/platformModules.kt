package di

import preferences.SettingWrapper
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sourceSetModules: Module
    get() = module { 
        
        single { 
            preferences.SettingWrapper().createSettings()
        }
    }
