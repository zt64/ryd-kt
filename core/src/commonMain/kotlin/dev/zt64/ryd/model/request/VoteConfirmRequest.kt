package dev.zt64.ryd.model.request

import kotlinx.serialization.Serializable

@Serializable
public data class VoteConfirmRequest(
    val solution: String,
    val userId: String,
    val videoId: String
)