package company.surious.treepoint.ui.common.fragments.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import company.surious.treepoint.MainNavigationDirections
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentLoginBinding
import company.surious.treepoint.ui.common.fragments.base.NavigationFragment
import company.surious.treepoint.ui.common.models.navigation.ErrorNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.LoginNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.MainNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.RegistrationNavigationDirection
import company.surious.treepoint.ui.common.view_models.LoginViewModel
import org.koin.android.ext.android.inject


class LoginFragment(override val navigationTag: String = "Login") : NavigationFragment() {

    private val loginViewModel: LoginViewModel by inject()

    private companion object {
        private const val SIGN_IN_REQUEST_CODE = 18
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        initBinding(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginNavigationSource()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let { loginViewModel.login(it) }
        }
    }

    private fun initBinding(binding: FragmentLoginBinding) {
        binding.eventHandler = LoginFragmentEventHandler()
        binding.isLoading = loginViewModel.isLoading
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeLoginNavigationSource() {
        loginViewModel.navigationSource.observe(viewLifecycleOwner, { direction ->
            val action =
                when (direction) {
                    is MainNavigationDirection ->
                        LoginFragmentDirections.actionLoginFragmentToMainFragment(direction.user)
                    is RegistrationNavigationDirection ->
                        LoginFragmentDirections.actionLoginFragmentToRegistrationFragment(direction.user)
                    is ErrorNavigationDirection ->
                        MainNavigationDirections.actionGlobalErrorFragment()
                    LoginNavigationDirection -> null
                }
            if (action != null) {
                findNavController().navigate(action)
            }
        })
    }

    inner class LoginFragmentEventHandler {
        fun onClick() {
            val signInIntent = loginViewModel.signInIntent
            startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE)
        }
    }

}