package company.surious.treepoint.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ActivityMainBinding
import company.surious.treepoint.ui.auth.AuthActivity
import company.surious.treepoint.ui.common.delegates.CommonToolbarDelegate
import company.surious.treepoint.ui.common.fragments.LoadingFragmentDirections

class MainActivity : AppCompatActivity() {

    companion object {
        private const val USER_KEY = "user"

        fun start(context: Context, registeredUser: RegisteredUser? = null) {
            val intent = Intent(context, MainActivity::class.java)
            registeredUser?.let {
                intent.putExtra(USER_KEY, it)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkStartData()
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

    private fun checkStartData() {
        if (intent != null) {
            val registeredUser = intent.getParcelableExtra<RegisteredUser>(USER_KEY)
            if (registeredUser == null) {
                startAuthActivity()
            } else {
                val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                    this,
                    R.layout.activity_main
                )
                setSupportActionBar(binding.mainAppBar.commonToolbar)
                findNavController(R.id.mainHostFragment).navigate(LoadingFragmentDirections.actionLoadingFragmentToTreeMapFragment())
            }
        } else {
            startAuthActivity()
        }
    }

    private fun startAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}