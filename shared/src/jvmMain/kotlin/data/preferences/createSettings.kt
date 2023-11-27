package data.preferences

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual fun createSettings(): Settings {
    val delegate = Preferences.userRoot()
    return PreferencesSettings(delegate)
}
