package company.surious.data.preferences

import android.content.SharedPreferences

fun SharedPreferences.putString(key: String, value: String?) {
    edit().putString(key, value).apply()
}