package company.surious.treepoint.ui.common.providers

import android.content.Context
import company.surious.treepoint.R

class TextResourcesProvider(private val context: Context) {

    fun getUsernameExistsText() = context.getString(R.string.username_exists)

    fun getUsernameInvalidText() = context.getString(R.string.invalid_username)
}