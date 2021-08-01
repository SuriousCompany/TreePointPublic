package company.surious.data.preferences

import android.content.Context
import android.content.SharedPreferences
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.preferences.UserPreferences

class TreeSharedPreferences(context: Context) : InnerPreferences, UserPreferences {
    private companion object {
        private const val FILE_NAME = "treeferences"
        private const val KEY_CURRENT_USER = "currentUser"
        private const val ZOOM_VALUE = "zoomValue"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    override var currentUserId: String?
        get() = preferences.getString(KEY_CURRENT_USER, null)
        set(value) {
            preferences.putString(KEY_CURRENT_USER, value)
        }

    override var zoomValue: Float
        get() = preferences.getFloat(ZOOM_VALUE, 18f)
        set(value) {
            preferences.putFloat(ZOOM_VALUE, value)
        }
}