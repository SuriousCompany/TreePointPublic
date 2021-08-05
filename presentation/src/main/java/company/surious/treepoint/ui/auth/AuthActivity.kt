package company.surious.treepoint.ui.auth

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ActivityAuthBinding
import company.surious.treepoint.ui.common.delegates.CommonToolbarDelegate

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAuthBinding>(this, R.layout.activity_auth)
        setSupportActionBar(binding.authAppBar.commonToolbar)
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        CommonToolbarDelegate.inflate(menuInflater, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return CommonToolbarDelegate.onOptionsItemSelected(this, item)
    }
}