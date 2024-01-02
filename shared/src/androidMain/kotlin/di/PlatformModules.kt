package di

import android.content.Context
import preferences.SettingWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sourceSetModules: Module = module {
    single { 
        preferences.SettingWrapper(get()).createSettings()
    }
}

class StorageManager(val context: Context){

    val freeStorage = flow{
        while (true){
            emit(getStorage())
            kotlinx.coroutines.delay(500)
        }
    }.flowOn(GlobalScope.coroutineContext)



    private fun getStorage() = 1
}
