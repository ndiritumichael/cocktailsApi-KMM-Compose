package data.preferences

import com.russhwolf.settings.ObservableSettings

expect class SettingWrapper {
    fun createSettings(): ObservableSettings
}
