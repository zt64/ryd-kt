package dev.zt64.ryd.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class VoteConfirmRequest(
    val solution: String,
    val userId: String,
    val videoId: String
)