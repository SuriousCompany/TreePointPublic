package company.surious.treepoint.ui.common.activities

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
        binding.creditsTextView.movementMethod = LinkMovementMethod.getInstance()
    }

}