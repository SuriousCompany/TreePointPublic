package company.surious.data.preferences

import android.content.SharedPreferences

fun SharedPreferences.putString(key: String, value: String?) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.putFloat(key: String, value: Float) {
    edit().putFloat(key, value).apply()
}