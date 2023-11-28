package data.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class SettingWrapper() {
    actual fun createSettings(): ObservableSettings {
        val delegate = Preferences.userRoot()
        return PreferencesSettings(delegate)
    }
}
