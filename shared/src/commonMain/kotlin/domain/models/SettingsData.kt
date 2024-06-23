package domain.models

enum class ThemeSettings(private val value : Int) {
    LIGHT(0),
    DARK(1),
    AUTO(2);

    fun getThemeSettings(number : Int): ThemeSettings = ThemeSettings.values().firstOrNull {
        it.value ==number
    } ?: AUTO
}

