package company.surious.treepoint.ui.common.extensions

import android.app.Activity
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnCompleteListener
import company.surious.domain.logging.logUnhandledError

private fun createLocationListener(listener: (Location?) -> Unit) =
    OnCompleteListener<Location> {
        with(it) {
            listener.invoke(
                if (isSuccessful) {
                    result
                } else {
                    logUnhandledError(exception!!, "location listener task is not successful")
                    null
                }
            )
        }
    }


fun FusedLocationProviderClient.getCurrentLocation(
    activity: Activity,
    listener: (Location?) -> Unit
) {
    try {
        val locationResult =
            getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            )
        locationResult.addOnCompleteListener(activity, createLocationListener(listener))
    } catch (e: SecurityException) {
        logUnhandledError(e, "getCurrentLocation SecurityException")
        listener.invoke(null)
    }
}

fun FusedLocationProviderClient.getLastLocation(
    activity: Activity,
    listener: (Location?) -> Unit
) {
    try {
        lastLocation.addOnCompleteListener(activity, createLocationListener(listener))
    } catch (e: SecurityException) {
        logUnhandledError(e, "getCurrentLocation SecurityException")
        listener.invoke(null)
    }
}

fun Location.toLatLng() = LatLng(latitude, longitude)