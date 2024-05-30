package dev.zt64.ryd.model.request

import kotlinx.serialization.Serializable

@Serializable
public data class RegisterRequest(
    val challenge: String,
    val solution: String,
    val difficulty: Int
)