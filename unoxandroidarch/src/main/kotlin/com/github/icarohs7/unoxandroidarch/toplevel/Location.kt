package com.github.icarohs7.unoxandroidarch.toplevel

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.annotation.RequiresPermission
import arrow.core.Try
import io.nlopez.smartlocation.SmartLocation
import splitties.init.appCtx
import splitties.systemservices.locationManager
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Try to get the current location using the given means by default:
 * - Try to request the current location using GPS
 * - Try to request the current location using the Network
 * - Try to get the last known location acquired through GPS
 * - Try to get the last known location acquired through the Network
 */
@RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
suspend fun getCurrentLocation(): Try<Location> {
    return Try {
        suspendCoroutine<Location?> { cont ->
            val smartLocation = SmartLocation.with(appCtx).location()
            val locationState = smartLocation.state()
            when {
                !locationState.locationServicesEnabled() -> cont.resume(null)
                !locationState.isAnyProviderAvailable -> cont.resume(null)
                else -> smartLocation.oneFix().start { cont.resume(it) }
            }
        }!!
    }
}

/**
 * Request the location from the given provider
 * ### Requires either [android.Manifest.permission.ACCESS_COARSE_LOCATION] or [android.Manifest.permission.ACCESS_FINE_LOCATION]
 */
@RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
suspend fun getLocationFromProvider(provider: String): Location? {
    return suspendCoroutine { continuation ->
        if (!locationManager.isProviderEnabled(provider)) {
            continuation.resume(null)
            return@suspendCoroutine
        }

        val listener = getLocationListener(continuation)
        locationManager.requestSingleUpdate(provider, listener, null)
    }
}

/**
 * Location listener using a coroutine continuation to return
 * the location
 */
private fun getLocationListener(continuation: Continuation<Location>): LocationListener {
    return object : LocationListener {
        override fun onLocationChanged(location: Location) {
            continuation.resume(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }
}