package company.surious.treepoint.ui.common.extensions

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

fun GoogleMap.addDraggableMarker(
    location: LatLng,
    icon: BitmapDescriptor,
    dragCallback: (LatLng) -> Unit
) {
    addMarker(MarkerOptions().draggable(true).icon(icon).position(location))
    setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
        override fun onMarkerDragStart(marker: Marker) {}

        override fun onMarkerDrag(marker: Marker) {}

        override fun onMarkerDragEnd(marker: Marker) {
            dragCallback.invoke(marker.position)
        }
    })
}

fun GoogleMap.addDraggableMarker(location: LatLng, hue: Float, dragCallback: (LatLng) -> Unit) {
    addDraggableMarker(location, BitmapDescriptorFactory.defaultMarker(hue), dragCallback)
}

fun GoogleMap.zoomToLocation(latLng: LatLng, zoom: Float) {
    moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
}