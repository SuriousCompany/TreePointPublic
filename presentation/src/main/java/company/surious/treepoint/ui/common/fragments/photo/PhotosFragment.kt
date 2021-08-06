package company.surious.treepoint.ui.common.fragments.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentPhotosBinding
import company.surious.treepoint.ui.common.view_models.photos.TreePointPhotosViewModel
import org.koin.android.ext.android.inject


class PhotosFragment : Fragment() {
    private lateinit var binding: FragmentPhotosBinding
    private val viewModel: TreePointPhotosViewModel by inject()
    private val args: PhotosFragmentArgs by navArgs()

    private val photoAdapter = StorageReferenceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        initBinding()
        return binding.root
    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.isLoading = viewModel.isLoading
        binding.photosRecyclerView.adapter = photoAdapter
        binding.photosRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.photos.observe(viewLifecycleOwner, { photos ->
            if (photos.isNotEmpty()) {
                photoAdapter.setData(photos)
            }
        })
        viewModel.refreshPhotos(args.treePointId)
    }
}