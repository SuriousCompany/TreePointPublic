package company.surious.treepoint.ui.common.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    companion object {
        private const val URL = "url"
        fun start(context: Context, url: String) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra(URL, url)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWebViewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.webView.loadUrl(intent.getStringExtra(URL)!!)
    }
}