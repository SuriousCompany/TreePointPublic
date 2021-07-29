package company.surious.treepoint.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import company.surious.domain.entities.RegisteredUser
import company.surious.treepoint.R
import company.surious.treepoint.ui.auth.AuthActivity
import company.surious.treepoint.ui.common.fragments.LoadingFragmentDirections

class MainActivity : FragmentActivity() {

    private var goToMapFragmentOnStart = false

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

    private fun checkStartData() {
        if (intent != null) {
            val registeredUser = intent.getParcelableExtra<RegisteredUser>(USER_KEY)
            if (registeredUser == null) {
                startAuthActivity()
            } else {
                goToMapFragmentOnStart = true
                setContentView(R.layout.activity_main)
            }
        } else {
            startAuthActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        if (goToMapFragmentOnStart) {
            findNavController(R.id.mainHostFragment).navigate(LoadingFragmentDirections.actionLoadingFragmentToTreeMapFragment())
        }
    }

    private fun startAuthActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}