package dev.zt64.ryd

import dev.zt64.ryd.model.Puzzle
import dev.zt64.ryd.model.Vote
import dev.zt64.ryd.model.Votes
import dev.zt64.ryd.model.request.RegisterRequest
import dev.zt64.ryd.model.request.VoteConfirmRequest
import dev.zt64.ryd.model.request.VoteRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.math.floor
import kotlin.random.Random

private const val USERAGENT = "github.com/zt64/ryd-kt"
private const val BASE64_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
private const val BASE_URL = "https://returnyoutubedislikeapi.com"

private fun httpClientConfig(apiUrl: String, userAgent: String): HttpClientConfig<*>.() -> Unit = {
    install(ContentNegotiation) {
        json(
            Json {
                // This is to prevent errors in case additional fields are added
                ignoreUnknownKeys = true
            }
        )
    }

    install(UserAgent) {
        agent = userAgent
    }

    defaultRequest {
        contentType(ContentType.Application.Json)
        url(apiUrl)
    }
}

/**
 * Client for interacting with the Return YouTube Dislike API
 */
public class RydClient(private val httpClient: HttpClient) : RydApi {
    public constructor(
        apiUrl: String = BASE_URL,
        userAgent: String = USERAGENT
    ) : this(HttpClient(httpClientConfig(apiUrl, userAgent)))

    public constructor(
        engine: HttpClientEngineFactory<*>,
        apiUrl: String = BASE_URL,
        userAgent: String = USERAGENT
    ) : this(HttpClient(engine, httpClientConfig(apiUrl, userAgent)))

    public constructor(
        engine: HttpClientEngine,
        apiUrl: String = BASE_URL,
        userAgent: String = USERAGENT
    ) : this(HttpClient(engine, httpClientConfig(apiUrl, userAgent)))

    override suspend fun getVotes(videoId: String): Votes {
        return httpClient.get("votes") {
            parameter("videoId", videoId)
        }.body<Votes>()
    }

    override suspend fun vote(
        videoId: String,
        userId: String,
        vote: Vote
    ) {
        val puzzleSolution = httpClient
            .post("$BASE_URL/interact/vote") {
                setBody(VoteRequest(userId, vote, videoId))
            }.body<Puzzle>()
            .solve()

        httpClient.post("interact/confirmVote") {
            setBody(VoteConfirmRequest(puzzleSolution, userId, videoId))
        }
    }

    override suspend fun like(videoId: String, userId: String) {
        vote(videoId, userId, Vote.LIKE)
    }

    override suspend fun dislike(videoId: String, userId: String) {
        vote(videoId, userId, Vote.DISLIKE)
    }

    override suspend fun removeVote(videoId: String, userId: String) {
        vote(videoId, userId, Vote.UNSET)
    }

    override suspend fun register(userId: String) {
        val puzzle = httpClient.get("puzzle/registration") {
            parameter("userId", userId)
        }.body<Puzzle>()

        val solution = puzzle.solve()

        httpClient.post("puzzle/registration") {
            parameter("userId", userId)
            setBody(RegisterRequest(userId, solution, puzzle.difficulty))
        }
    }

    public companion object {
        /**
         * Generates a random user ID
         */
        public fun generateUserId(): String = CharArray(36) {
            BASE64_CHARSET[floor((Random.nextFloat() * 36)).toInt()]
        }.concatToString()
    }
}