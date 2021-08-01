package company.surious.treepoint.ui.common.fragments.create_tree_point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import company.surious.domain.entities.TreePointDraft
import company.surious.domain.entities.TreeType
import company.surious.domain.logging.logNavigation
import company.surious.domain.use_case.tree_point.CreateTreePointUseCase
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentCreateTreePointBinding
import company.surious.treepoint.ui.common.fragments.dialogs.month_picker.MonthPickerDialogFragment
import company.surious.treepoint.ui.common.view_models.tree_type.AllTreeTypesViewModel
import org.koin.android.ext.android.inject

class CreateTreePointFragment : Fragment() {

    private val arguments: CreateTreePointFragmentArgs by navArgs()
    private val allTreeTypesViewModel: AllTreeTypesViewModel by inject()
    private val commentText = MutableLiveData<String>().apply { value = "" }
    private val draft = TreePointDraft()
    private val canGoNext = MutableLiveData<Boolean>().apply { value = false }

    private val createTreePointUseCase: CreateTreePointUseCase by inject()
    private var selectedFruitionSeason: Pair<Int, Int>? = null

    private lateinit var binding: FragmentCreateTreePointBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        draft.lat = arguments.latitude.toDouble()
        draft.lng = arguments.longitude.toDouble()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_tree_point, container, false)
        initBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allTreeTypesViewModel.startObserving()
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }

    private fun listenForMonthRangePick(): LiveData<Pair<Int, Int>?> =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Pair<Int, Int>?>(
            MonthPickerDialogFragment.PICKED_MONTH_RANGE
        ) as LiveData<Pair<Int, Int>?>

    private fun showMonthPicker() {
        val direction =
            selectedFruitionSeason?.let {
                CreateTreePointFragmentDirections.actionCreateTreePointFragmentToMonthPickerDialogFragment(
                    it.first,
                    it.second
                )
            }
                ?: CreateTreePointFragmentDirections.actionCreateTreePointFragmentToMonthPickerDialogFragment()
        findNavController().navigate(direction)
    }

    private fun initBinding() {
        val fruitionSource = listenForMonthRangePick()
        observeSelectedFruitionSeason(fruitionSource)
        observeComment()
        with(binding) {
            fruitingRange = fruitionSource
            treeTypesAdapter = TreeTypeSpinnerAdapter(requireContext())
            treeTypes = allTreeTypesViewModel.treeTypes
            lifecycleOwner = viewLifecycleOwner
            eventHandler = CreateTreePointFragmentEventHandler()
            comment = commentText
            canGoNext = this@CreateTreePointFragment.canGoNext
        }
    }

    private fun observeSelectedFruitionSeason(source: LiveData<Pair<Int, Int>?>) {
        source.observe(viewLifecycleOwner, {
            selectedFruitionSeason = it
            canGoNext.value = selectedFruitionSeason?.let { checkedFruitingSeason ->
                draft.ripeStartMonth = checkedFruitingSeason.first
                draft.ripeEndMonth = checkedFruitingSeason.second
                true
            } ?: false
        })
    }

    private fun observeComment() {
        commentText.observe(viewLifecycleOwner, { comment ->
            draft.creatorComment = comment
        })
    }

    inner class CreateTreePointFragmentEventHandler {
        fun onTreeTypeSelected(treeType: TreeType) {
            draft.type = treeType
        }

        fun onSelectFruitingSeasonClicked() {
            showMonthPicker()
        }

        fun onCreateTreePointButtonClicked() {
            //TODO fixit
            createTreePointUseCase.execute(draft).subscribe()
        }
    }
}