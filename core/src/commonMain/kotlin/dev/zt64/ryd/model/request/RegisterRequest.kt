package dev.zt64.ryd.model.request

import kotlinx.serialization.Serializable

/**
 * Represents a request to be sent to the server for registering a user
 */
@Serializable
public data class RegisterRequest(
    val challenge: String,
    val solution: String,
    val difficulty: Int
)