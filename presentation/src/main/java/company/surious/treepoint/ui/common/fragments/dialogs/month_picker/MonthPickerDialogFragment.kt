package company.surious.treepoint.ui.common.fragments.dialogs.month_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import company.surious.domain.logging.LogTags
import company.surious.domain.logging.logNavigation
import company.surious.domain.logging.logVerbose
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentMonthPickerDialogBinding
import company.surious.treepoint.ui.common.binding.lists.range.RangeRecyclerViewEventHandler

class MonthPickerDialogFragment : DialogFragment() {
    companion object {
        const val PICKED_MONTH_RANGE = "pickerMonthRange"
    }

    private var selectedMonths: Pair<Int, Int>? = null

    private val args: MonthPickerDialogFragmentArgs by navArgs()

    private lateinit var binding: FragmentMonthPickerDialogBinding
    private lateinit var months: List<SelectableMonth>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_month_picker_dialog,
            container,
            false
        )
        initBinding()
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

    private fun initBinding() {
        months = requireContext().resources.getStringArray(R.array.months).map(::SelectableMonth)
        val monthSource = MutableLiveData<List<SelectableMonth>>().apply {
            value = months
        }
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            eventHandler = MonthPickerDialogEventHandler()
            adapter = SelectableMonthAdapter()
            layoutManager = GridLayoutManager(requireContext(), 3)
            monthData = monthSource
        }
        selectMonths(monthSource)
    }

    private fun selectMonths(monthSource: LiveData<List<SelectableMonth>>) {
        logVerbose(LogTags.DEBUG, "selectMonths")
        monthSource.observe(viewLifecycleOwner, {
            logVerbose(LogTags.DEBUG, "monthSource triggered")
            val startMonth = args.firstFruitingMonth
            val endMonth = args.lastFruitingMonth
            if (startMonth != -1 && endMonth != -1) {
                logVerbose(LogTags.DEBUG, "selecting months : $startMonth - $endMonth")
                binding.adapter!!.selectMonthRange(startMonth, endMonth)
            }
        })
    }

    inner class MonthPickerDialogEventHandler : RangeRecyclerViewEventHandler<SelectableMonth> {
        override fun onNothingSelected() {
            super.onNothingSelected()
            selectedMonths = null
        }

        override fun onRangeSelected(firstItem: SelectableMonth, lastItem: SelectableMonth) {
            super.onRangeSelected(firstItem, lastItem)
            selectedMonths = Pair(months.indexOf(firstItem), months.indexOf(lastItem))
        }

        fun onCancelButtonClicked() {
            selectedMonths = null
            dismiss()
        }

        fun onOkButtonClicked() {
            if (selectedMonths != null) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    PICKED_MONTH_RANGE,
                    selectedMonths
                )
            }
            dismiss()
        }
    }
}