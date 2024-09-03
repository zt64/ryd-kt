package dev.zt64.ryd.model.request

import kotlinx.serialization.Serializable

/**
 * Represents a request to be sent to the server for confirming a vote after
 * the challenge has been solved
 */
@Serializable
public data class VoteConfirmRequest(
    val solution: String,
    val userId: String,
    val videoId: String
)