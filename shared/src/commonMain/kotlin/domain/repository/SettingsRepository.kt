package domain.repository

import domain.models.ThemeSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
 suspend fun savePreference(key: String, value: Int)

 val getPrefferedTheme : Flow<ThemeSettings>

}