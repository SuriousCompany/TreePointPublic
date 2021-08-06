package company.surious.treepoint.ui.common.fragments.photo

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import company.surious.domain.logging.logVerbose
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentUploadPhotosBinding
import company.surious.treepoint.ui.common.binding.lists.RecyclerViewEventHandler
import company.surious.treepoint.ui.common.view_models.photos.UploadPhotosViewModel
import org.koin.android.ext.android.inject
import java.io.File

class UploadPhotosFragment : Fragment() {
    companion object {
        private const val MAX_PHOTOS_FOR_UPLOAD = 5
    }

    private val photoAdapter = UriPhotoAdapter()
    private val isLoading = MutableLiveData<Boolean>().apply { value = true }
    private val isAddingEnabled = MutableLiveData<Boolean>().apply { value = false }
    private val isDoneButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    private val arguments: UploadPhotosFragmentArgs by navArgs()
    private val uploadPhotosViewModel: UploadPhotosViewModel by inject()

    private var lastUri: Uri? = null

    private lateinit var binding: FragmentUploadPhotosBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                logVerbose("", "")//TODO
            }
        }
    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { receivedPicture ->
            if (receivedPicture) {
                lastUri?.let(::onPhotoReceived)
            } else {
                logVerbose("", "")//TODO
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_upload_photos, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.eventHandler = PhotosFragmentEventHandler()
        binding.photosRecyclerView.adapter = photoAdapter
        binding.photosRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.isAddingEnabled = isAddingEnabled
        binding.isDoneButtonEnabled = isDoneButtonEnabled
        binding.isLoading = isLoading
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoAdapter.eventHandler = binding.eventHandler
        checkPermission()
        observeUploadingResult()
    }

    private fun checkPermission() {
        val cameraPermission = Manifest.permission.CAMERA
        val permissionResult = ContextCompat.checkSelfPermission(
            requireContext(),
            cameraPermission
        )
        if (permissionResult == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            //TODO check shouldShowRequestPermissionRationale
            requestPermissionLauncher.launch(cameraPermission)
        }
    }

    private fun onPermissionGranted() {
        isLoading.value = false
        refreshAddingButton()
    }

    private fun onPhotoReceived(uri: Uri) {
        photoAdapter.add(uri)
        refreshButtons()
        //TODO uploadUseCase.execute(uri).subscribe()
    }

    private fun createNewPhotoUri(): Uri {
        val tmpFile =
            File.createTempFile("${System.currentTimeMillis()}", ".png", requireActivity().cacheDir)
                .apply {
                    createNewFile()
                    deleteOnExit()
                }
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            tmpFile
        )
    }

    private fun takePhoto() {
        lastUri = createNewPhotoUri()
        photoLauncher.launch(lastUri)
    }

    private fun refreshAddingButton() {
        isAddingEnabled.value = photoAdapter.data.size < MAX_PHOTOS_FOR_UPLOAD
    }

    private fun refreshDoneButton() {
        isDoneButtonEnabled.value = photoAdapter.data.size > 0
    }

    private fun refreshButtons() {
        refreshAddingButton()
        refreshDoneButton()
    }

    private fun removePhoto(index: Int, uri: Uri) {
        //TODO show a dialog about the photo removing
        photoAdapter.remove(index)
        refreshButtons()
    }

    private fun returnToTheMap() {
        Toast.makeText(
            requireActivity(),
            getString(R.string.tree_point_created),
            Toast.LENGTH_LONG
        ).show()
        val action =
            UploadPhotosFragmentDirections.actionPhotosFragmentToTreeMapFragment(
                arguments.lat,
                arguments.lng
            )
        findNavController().navigate(action)
    }

    private fun observeUploadingResult() {
        uploadPhotosViewModel.uploadingResult.observe(viewLifecycleOwner, { uploadedPhotos ->
            if (uploadedPhotos != UploadPhotosViewModel.NOT_UPLOADED) {
                returnToTheMap()
            }
        })
    }

    inner class PhotosFragmentEventHandler : RecyclerViewEventHandler<Uri> {
        fun onAddButtonClicked() {
            takePhoto()
        }

        fun onDoneButtonClicked() {
            uploadPhotosViewModel.uploadPhotos(arguments.treePointId, photoAdapter.data)
        }

        override fun onItemClicked(index: Int, item: Uri) {
            //TODO show fullscreen image
        }

        override fun onItemLongClicked(index: Int, item: Uri) {
            removePhoto(index, item)
        }
    }
}