package pl.hackyeah.backend.entity

import kotlinx.serialization.Serializable

@Serializable
data class GeoLocation(val lat: Double, val lng: Double)
