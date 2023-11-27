package data.preferences

import com.russhwolf.settings.Settings

expect class SettingWrapper {
    fun createSettings(): Settings
}
