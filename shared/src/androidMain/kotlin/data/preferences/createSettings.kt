package data.preferences

import android.content.Context

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings


actual class SettingWrapper(private val context: Context) {
    actual fun createSettings(): Settings {

        val delegate = context.getSharedPreferences("cocktail_preferences", Context.MODE_PRIVATE)

        return SharedPreferencesSettings(delegate)
    }
}