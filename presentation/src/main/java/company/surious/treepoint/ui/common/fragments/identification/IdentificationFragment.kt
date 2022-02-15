package company.surious.treepoint.ui.common.fragments.identification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import company.surious.domain.entities.identification.HealthAssessmentMode
import company.surious.domain.entities.identification.result.IdentificationSuggestion
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentIdentificationBinding
import company.surious.treepoint.ui.common.activities.WebViewActivity
import company.surious.treepoint.ui.common.binding.lists.RecyclerViewEventHandler
import company.surious.treepoint.ui.common.view_models.identification.IdentificationUsageInfoViewModel
import company.surious.treepoint.ui.common.view_models.identification.IdentificationViewModel
import org.koin.android.ext.android.inject


class IdentificationFragment : Fragment() {
    private val identificationUsageInfoViewModel: IdentificationUsageInfoViewModel by inject()
    private val identificationViewModel: IdentificationViewModel by inject()
    private val suggestionsAdapter = IdentificationSuggestionAdapter()
    private val arguments: IdentificationFragmentArgs by navArgs()
    private val healthAssessmentMode = MutableLiveData<HealthAssessmentMode>().apply {
        value = HealthAssessmentMode.NEVER
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentIdentificationBinding.inflate(inflater, container, false)
        initBinding(binding)
        return binding.root
    }

    private fun initBinding(binding: FragmentIdentificationBinding) {
        val handler = IdentificationFragmentEventHandler()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.usageInfo = identificationUsageInfoViewModel.usageInfo
        binding.isLoading = identificationViewModel.isLoading
        binding.healthAssessmentMode = healthAssessmentMode
        binding.eventHandler = handler
        binding.identificationResult = identificationViewModel.identificationResult
        binding.suggestionsRecyclerView.adapter = suggestionsAdapter
        binding.suggestionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        suggestionsAdapter.eventHandler = handler
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        identificationUsageInfoViewModel.refreshUsageInfo()
        observeIdentificationResult()
        observeCredits()
    }

    private fun observeIdentificationResult() {
        identificationViewModel.identificationResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                identificationUsageInfoViewModel.refreshUsageInfo()
                suggestionsAdapter.setData(result.suggestions)
            }
        }
    }

    private fun observeCredits() {
        identificationViewModel.addedCreditsEvent.observe(viewLifecycleOwner) { event ->
            val rootView = view
            if (rootView != null) {
                Snackbar.make(
                    rootView,
                    getString(R.string.got_credits, event.addedCredits, event.allCredits),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    inner class IdentificationFragmentEventHandler :
        RecyclerViewEventHandler<IdentificationSuggestion> {

        override fun onItemClicked(index: Int, item: IdentificationSuggestion) {
            with(item.plantDetails.urls) {
                WebViewActivity.start(
                    requireContext(),
                    uk ?: ru ?: en ?: "http://amishrakefight.org/gfy/"
                )
            }
        }

        fun onIdentifyButtonClicked() {
            identificationViewModel.identify(
                requireActivity().contentResolver,
                arguments.photos.photos,
                healthAssessmentMode.value!!
            )
        }
    }
}