package company.surious.treepoint.ui.common.delegates

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import company.surious.treepoint.R
import company.surious.treepoint.ui.common.activities.AboutActivity

object CommonToolbarDelegate {
    fun inflate(inflater: MenuInflater, menu: Menu?) {
        inflater.inflate(R.menu.menu_common_toolbar, menu)
    }

    fun onOptionsItemSelected(context: Context, item: MenuItem): Boolean =
        if (item.itemId == R.id.menuItemAbout) {
            context.startActivity(Intent(context, AboutActivity::class.java))
            true
        } else {
            false
        }
}