package dev.zt64.ryd.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * @property id The ID of the video
 * @property likes The number of likes the video has
 * @property dislikes The number of dislikes the video has
 * @property dateCreated The date the video was created
 * @property deleted Whether the video has been deleted
 * @property rating The rating of the video
 * @property viewCount The number of views the video has
 */
@Serializable
public data class Votes internal constructor(
    val id: String,
    val likes: Int,
    val dislikes: Int,
    val dateCreated: Instant,
    val deleted: Boolean,
    val rating: Double,
    val viewCount: Int
)