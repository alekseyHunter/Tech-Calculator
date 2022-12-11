package my.tech.calculator.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import my.tech.calculator.models.ThemeMode

class PreferencesManager(private val context: Context) {
    fun getCurrentTheme(): ThemeMode {
        val pref = context.getSharedPreferences("tech.calculator", AppCompatActivity.MODE_PRIVATE)
        val theme = pref.getInt("theme", ThemeMode.DARK.value)
        return ThemeMode.convertToThemeMode(theme)
    }

    fun setCurrentTheme(themeMode: ThemeMode) {
        val pref = context.getSharedPreferences("tech.calculator", AppCompatActivity.MODE_PRIVATE)

        pref.edit {
            putInt("theme", themeMode.value)
        }
    }
}