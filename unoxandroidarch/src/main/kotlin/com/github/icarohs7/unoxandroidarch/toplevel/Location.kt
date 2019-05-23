package com.github.icarohs7.unoxandroidarch.toplevel

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.annotation.RequiresPermission
import arrow.core.Try
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
        safeRun { getLocationFromProvider(LocationManager.GPS_PROVIDER) }
                ?: safeRun { getLocationFromProvider(LocationManager.NETWORK_PROVIDER) }
                ?: safeRun { locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) }
                ?: safeRun { locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) }!!
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