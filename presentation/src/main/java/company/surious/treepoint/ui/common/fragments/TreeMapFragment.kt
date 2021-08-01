package company.surious.treepoint.ui.common.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import company.surious.domain.entities.TreePoint
import company.surious.domain.logging.logNavigation
import company.surious.treepoint.R
import company.surious.treepoint.databinding.FragmentTreeMapBinding
import company.surious.treepoint.ui.common.fragments.base.BaseMapFragment
import company.surious.treepoint.ui.common.view_models.tree_point.AllTreePointsViewModel
import org.koin.android.ext.android.inject


class TreeMapFragment : BaseMapFragment() {

    private val permission = Manifest.permission.ACCESS_FINE_LOCATION
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentTreeMapBinding

    private val allTreePointsViewModel: AllTreePointsViewModel by inject()

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                getMapAsync(::initMap)
            } else {
                TODO()
            }
        }

    //TODO replace with requesting icon from cache/server
    private val appleIcon: BitmapDescriptor by lazy {
        BitmapDescriptorFactory.fromBitmap(
            createBitmapFromVector(R.drawable.ic_apple)
        )
    }

    override val mapView: MapView
        get() = binding.treeMapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tree_map, container, false)
        binding.eventHandler = TreeMapEventHandler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        checkPermission()
    }

    override fun onResume() {
        super.onResume()
        logNavigation()
    }

    private fun onMarkerClicked(marker: Marker): Boolean {
        val treePoint =
            allTreePointsViewModel.treePoints.value?.firstOrNull() { it.id == marker.tag }
        val foundTreePoint = treePoint != null
        if (foundTreePoint) {
            val action =
                TreeMapFragmentDirections.actionTreeMapFragmentToDisplayTreePointFragment(treePoint!!)
            findNavController().navigate(action)
        }
        return foundTreePoint
    }

    private fun checkPermission() {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireActivity(), permission
        ) == PackageManager.PERMISSION_GRANTED
        if (isPermissionGranted) {
            getMapAsync(::initMap)
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun initMap(googleMap: GoogleMap) {
        googleMap.isMyLocationEnabled = true
        zoomToCurrentLocation(googleMap)
        googleMap.setOnMarkerClickListener(::onMarkerClicked)
        observeTreePoints(googleMap)
    }

    private fun requestCurrentLocation(listener: (Double, Double) -> Unit) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        listener.invoke(
                            lastKnownLocation.latitude,
                            lastKnownLocation.longitude
                        )
                    }
                } else {
                    TODO()
                    /*googleMap?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false*/
                }
            }
        } catch (e: SecurityException) {
            TODO()
            //Log.e("Exception: %s", e.message, e)
        }
    }

    private fun zoomToCurrentLocation(googleMap: GoogleMap) {
        requestCurrentLocation { latitude, longitude ->
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 20f)
            )
        }
    }

    private fun observeTreePoints(googleMap: GoogleMap) {
        allTreePointsViewModel.treePoints.observe(viewLifecycleOwner,
            { treePoints ->
                treePoints.forEach {
                    val marker = googleMap.addMarker(createTreePointMarker(it))
                    marker.tag = it.id
                }
            })
        allTreePointsViewModel.startObserving()
    }

    private fun createTreePointMarker(treePoint: TreePoint) =
        MarkerOptions()
            .position(LatLng(treePoint.lat, treePoint.lng))
            .title(treePoint.type.typeName)//TODO add localization
            .icon(appleIcon)


    private fun createBitmapFromVector(@DrawableRes resId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(requireContext(), resId)!!
        val width = 100//TODO add adaptation for the different densities
        val height = 100
        drawable.setBounds(0, 0, width, height)
        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
        // add the tint if it exists
        /*tintColor?.let {
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, it))
        }*/
        // draw it onto the bitmap
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return bitmap
    }

    inner class TreeMapEventHandler {
        fun onAddButtonClicked() {
            requestCurrentLocation { latitude, longitude ->
                val action =
                    TreeMapFragmentDirections.actionTreeMapFragmentToCreateTreePointFragment(
                        latitude.toFloat(),
                        longitude.toFloat()
                    )
                findNavController().navigate(action)
            }
        }
    }
}