package dev.zt64.ryd.model.request

import dev.zt64.ryd.model.Vote
import kotlinx.serialization.Serializable

/**
 * Represents a request to be sent to the server for voting on a video
 */
@Serializable
public data class VoteRequest(
    val userId: String,
    val value: Vote,
    val videoId: String
)