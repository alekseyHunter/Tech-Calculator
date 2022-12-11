package my.tech.calculator.models

sealed class ThemeMode(val value: Int) {

    object DAY: ThemeMode(0)
    object DARK: ThemeMode(1)

    companion object{
        fun convertToThemeMode(value: Int): ThemeMode {
            return when(value){
                DAY.value -> DAY
                DARK.value -> DARK
                else -> throw Exception("Not found ThemeMode with value")
            }
        }
    }
}
