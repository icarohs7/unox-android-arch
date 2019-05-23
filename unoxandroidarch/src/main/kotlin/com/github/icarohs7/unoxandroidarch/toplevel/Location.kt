package com.github.icarohs7.unoxandroidarch.toplevel

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import arrow.core.Try
import splitties.systemservices.locationManager
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Return the last know location if available or request the
 * current location using the network provider. **Needs permission**
 */
@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(): Try<Location> {
    return Try {
        val locManager = locationManager
        val lastGpsLoc: Location? = locManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER)
        val lastNetworkLoc: Location? = locManager.getLastKnownLocation(
                LocationManager.NETWORK_PROVIDER)

        suspend fun getLoc(provider: String): Location {
            return suspendCoroutine { continuation ->
                val locationListener = getLocationListener(continuation)
                locManager.requestSingleUpdate(provider, locationListener, null)
            }
        }

        safeRun { lastGpsLoc }
                ?: safeRun { lastNetworkLoc }
                ?: safeRun {
                    getLoc(LocationManager.GPS_PROVIDER)
                }
                ?: safeRun {
                    getLoc(LocationManager.NETWORK_PROVIDER)
                }!!
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

        override fun onProviderDisabled(provider: String) {
            continuation.resumeWithException(UnsupportedOperationException("Provider disabled"))
        }
    }
}