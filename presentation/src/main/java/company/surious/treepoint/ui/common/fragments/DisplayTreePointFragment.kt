package company.surious.treepoint.ui.common.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import company.surious.domain.entities.TreePoint
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentDisplayTreePointBinding

//TODO observe provided tree point
class DisplayTreePointFragment : DialogFragment() {

    private val arguments: DisplayTreePointFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDisplayTreePointBinding>(
            inflater, R.layout.fragment_display_tree_point,
            container,
            false
        )
        initBinding(binding)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }

    private fun initBinding(binding: FragmentDisplayTreePointBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.treePoint = MutableLiveData<TreePoint>().apply {
            value = arguments.treePoint
        }
        binding.eventHandler = DisplayTreePointFragmentEventHandler()
    }

    inner class DisplayTreePointFragmentEventHandler {
        fun photosButtonClicked() {
            val destination =
                DisplayTreePointFragmentDirections.actionDisplayTreePointFragmentToPhotosFragment(
                    arguments.treePoint.id
                )
            findNavController().navigate(destination)
        }
    }
}