package company.surious.treepoint.ui.common.fragments.base

import androidx.fragment.app.Fragment
import company.surious.domain.logging.logNav

abstract class NavigationFragment : Fragment() {
    protected abstract val navigationTag: String

    override fun onStart() {
        super.onStart()
        logNav(navigationTag)
    }


}