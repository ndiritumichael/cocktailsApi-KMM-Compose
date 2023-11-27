package data.preferences

import android.content.Context

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual fun createSettings(context : Context): Settings {
    val delegate = context.getSharedPreferences("bloom_preferences", Context.MODE_PRIVATE)

return
}