package screens.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.models.ThemeSettings
import domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SettingPresenter(private val repository: SettingsRepository):ScreenModel {


    val themePreference = repository.getPrefferedTheme.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(5000),
        ThemeSettings.AUTO
    )
}