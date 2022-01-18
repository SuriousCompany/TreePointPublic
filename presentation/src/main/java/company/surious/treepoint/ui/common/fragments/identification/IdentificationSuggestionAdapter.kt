package company.surious.treepoint.ui.common.fragments.identification

import company.surious.domain.entities.identification.result.IdentificationSuggestion
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ItemIdentificationSuggestionBinding
import company.surious.treepoint.ui.common.binding.lists.BindableRecyclerAdapter
import company.surious.treepoint.ui.common.binding.lists.BindableViewHolder

class IdentificationSuggestionAdapter(override val itemLayoutRes: Int = R.layout.item_identification_suggestion) :
    BindableRecyclerAdapter<ItemIdentificationSuggestionBinding, IdentificationSuggestion, IdentificationSuggestionAdapter.SuggestionViewHolder>() {

    class SuggestionViewHolder(binding: ItemIdentificationSuggestionBinding) :
        BindableViewHolder<ItemIdentificationSuggestionBinding, IdentificationSuggestion>(binding) {

        override fun bind(item: IdentificationSuggestion) {
            binding.suggestion = item
        }

    }

    override fun createViewHolder(binding: ItemIdentificationSuggestionBinding): SuggestionViewHolder =
        SuggestionViewHolder(binding)
}