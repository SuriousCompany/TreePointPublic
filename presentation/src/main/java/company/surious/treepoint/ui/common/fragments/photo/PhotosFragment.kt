package company.surious.treepoint.ui.common.fragments.photo

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import company.surious.domain.logging.logVerbose
import company.surious.domain.use_case.cloud_storage.UploadTreePointPhotoUseCase
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentPhotosBinding
import org.koin.android.ext.android.inject
import java.io.File


//TODO block add button before permission received
class PhotosFragment : Fragment() {
    private lateinit var binding: FragmentPhotosBinding
    private val uploadUseCase: UploadTreePointPhotoUseCase by inject()
    private val uri by lazy {
        val tmpFile =
            File.createTempFile("TreePointPhoto", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                //deleteOnExit()
            }
        FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            tmpFile
        )
    }
    private val isAddingEnabled = MutableLiveData<Boolean>().apply { value = false }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                logVerbose("", "")
            }
        }
    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { receivedPicture ->
            if (receivedPicture) {
                onPhotoReceived(uri)
            } else {
                logVerbose("", "")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        binding.eventHandler = PhotosFragmentEventHandler()
        binding.isAddingEnabled = isAddingEnabled
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
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
        isAddingEnabled.value = true
    }

    private fun requestPhoto() {
        photoLauncher.launch(uri)
    }

    private fun onPhotoReceived(uri: Uri) {
        binding.imagePreview.setImageURI(uri)
        uploadUseCase.execute(uri).subscribe()
    }

    inner class PhotosFragmentEventHandler {
        fun onAddButtonClicked() {
            requestPhoto()
        }
    }
}