package dev.zt64.ryd

import dev.zt64.ryd.model.Vote
import dev.zt64.ryd.model.Votes

public interface RydApi {
    /**
     * Gets the votes for a video
     *
     * @param videoId
     */
    public suspend fun get(videoId: String): Votes

    /**
     * Adds a vote to a video
     *
     * @param videoId
     * @param vote
     */
    public suspend fun vote(
        videoId: String,
        userId: String,
        vote: Vote
    )

    /**
     * Set vote to like
     *
     * @param videoId
     * @param userId
     */
    public suspend fun like(
        videoId: String,
        userId: String
    )

    /**
     * Set vote to dislike
     *
     * @param videoId
     * @param userId
     */
    public suspend fun dislike(
        videoId: String,
        userId: String
    )

    /**
     * Remove a vote from a video
     *
     * @param videoId
     * @param userId
     */
    public suspend fun removeVote(
        videoId: String,
        userId: String
    )

    /**
     * Registers a user ID with the API
     *
     * @param userId The user ID to register
     */
    public suspend fun register(userId: String)
}