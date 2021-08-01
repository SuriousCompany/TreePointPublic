package company.surious.treepoint.ui.common.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.MainNavigationDirections
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentRegistrationBinding
import company.surious.treepoint.ui.common.models.navigation.ErrorNavigationDirection
import company.surious.treepoint.ui.common.models.navigation.MainNavigationDirection
import company.surious.treepoint.ui.common.view_models.RegistrationViewModel
import org.koin.android.ext.android.inject


class RegistrationFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentRegistrationBinding>(
            inflater,
            R.layout.fragment_registration,
            container,
            false
        )
        initBinding(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigationSource()
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }

    private fun observeNavigationSource() {
        registrationViewModel.navigationSource.observe(
            viewLifecycleOwner,
            { direction ->
                val action = when (direction) {
                    is ErrorNavigationDirection ->
                        MainNavigationDirections.actionGlobalErrorFragment()
                    is MainNavigationDirection ->
                        RegistrationFragmentDirections.actionRegistrationFragmentToMainFragment(
                            direction.user
                        )
                    else -> null
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }
        )
    }

    private fun initBinding(binding: FragmentRegistrationBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.username = registrationViewModel.userName
        binding.errorText = registrationViewModel.errorText
        binding.isLoading = registrationViewModel.isLoading
        binding.eventHandler = RegistrationFragmentEventHandler()
    }

    inner class RegistrationFragmentEventHandler {
        fun onRegisterButtonClicked() {
            registrationViewModel.register()
        }
    }
}