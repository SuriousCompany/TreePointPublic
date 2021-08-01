package company.surious.treepoint.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }
}