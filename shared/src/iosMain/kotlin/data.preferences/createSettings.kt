package data.preferences

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual fun createSettings(): Settings {
    val delegate = NSUserDefaults.standardUserDefaults
    return NSUserDefaultsSettings(delegate)
}
