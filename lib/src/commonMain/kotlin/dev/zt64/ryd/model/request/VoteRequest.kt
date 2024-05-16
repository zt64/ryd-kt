package dev.zt64.ryd.model.request

import dev.zt64.ryd.model.Vote
import kotlinx.serialization.Serializable

@Serializable
internal data class VoteRequest(
    val userId: String,
    val value: Vote,
    val videoId: String
)