package company.surious.treepoint.ui.common.fragments.identification

import com.bumptech.glide.Glide
import company.surious.domain.entities.identification.result.IdentificationSuggestion
import company.surious.domain.logging.logDebug
import company.surious.treepoint.R
import company.surious.treepoint.databinding.ItemIdentificationSuggestionBinding
import company.surious.treepoint.ui.common.binding.lists.BindableRecyclerAdapter
import company.surious.treepoint.ui.common.binding.lists.BindableViewHolder

class IdentificationSuggestionAdapter(override val itemLayoutRes: Int = R.layout.item_identification_suggestion) :
    BindableRecyclerAdapter<ItemIdentificationSuggestionBinding, IdentificationSuggestion, IdentificationSuggestionAdapter.SuggestionViewHolder>() {

    class SuggestionViewHolder(binding: ItemIdentificationSuggestionBinding) :
        BindableViewHolder<ItemIdentificationSuggestionBinding, IdentificationSuggestion>(binding) {

        override fun bind(item: IdentificationSuggestion) {
            with(binding) {
                suggestion = item
                val images = item.images
                logDebug("displaying suggestion with ${images.size} images: ${images.joinToString { it.url }}")
                if (images.isNotEmpty()) {
                    Glide.with(root).load(images[0].url).into(firstSuggestionImage)
                    if (images.size > 1) {
                        Glide.with(root).load(images[1].url).fitCenter().into(secondSuggestionImage)
                    }
                }
            }
        }

    }

    override fun createViewHolder(binding: ItemIdentificationSuggestionBinding): SuggestionViewHolder =
        SuggestionViewHolder(binding)
}