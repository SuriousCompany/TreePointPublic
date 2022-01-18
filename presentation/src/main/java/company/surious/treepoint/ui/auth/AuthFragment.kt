package company.surious.treepoint.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import company.surious.domain.entities.users.RegisteredUser
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.MainNavigationDirections
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentAuthBinding
import company.surious.treepoint.ui.common.models.navigation.ErrorNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.LoginNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.MainNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.RegistrationNavigationDirection
import company.surious.treepoint.ui.common.view_models.CheckCurrentUserViewModel
import company.surious.treepoint.ui.main.MainActivity
import org.koin.android.ext.android.inject

//TODO merge it with LoginFragment
class AuthFragment : Fragment() {

    private val checkCurrentUserViewModel: CheckCurrentUserViewModel by inject()

    private val args: AuthFragmentArgs by navArgs()

    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_auth,
            container,
            false
        )
        initBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigationSource()
        checkCurrentUserViewModel.checkUser(args.registeredUser)
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.isLoading = checkCurrentUserViewModel.loadingState
    }

    private fun observeNavigationSource() {
        checkCurrentUserViewModel.navigationSource.observe(viewLifecycleOwner, { direction ->
            val action = when (direction) {
                is ErrorNavigationDirection -> MainNavigationDirections.actionGlobalErrorFragment()
                LoginNavigationDirection ->
                    AuthFragmentDirections.actionMainFragmentToLoginFragment()
                is RegistrationNavigationDirection ->
                    AuthFragmentDirections.actionMainFragmentToRegistrationFragment()
                is MainNavigationDirection -> {
                    startMainActivity(direction.user)
                    null
                }
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        })
    }

    private fun startMainActivity(registeredUser: RegisteredUser) {
        activity?.let {
            MainActivity.start(it, registeredUser)
            it.finish()
        }
    }
}