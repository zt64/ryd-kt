package dev.zt64.ryd

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private data class TestData(
    val videoId: String,
    val challenge: String,
    val solution: String,
    val difficulty: Int = 15,
    val likes: Int,
    val dislikes: Int
)

private val data = listOf(
    TestData(
        videoId = "dQw4w9WgXcQ",
        challenge = "BiCDTObtH5yq7+GTfRulAA==",
        solution = "jAMAAA==",
        likes = 1000000,
        dislikes = 1
    )
)

class RydClientTest {
    @Test
    fun testUserIdGenerator() = runTest {
        assertTrue { RydClient.generateUserId().length == 36 }
    }

    @Test
    fun testGetVotes() = runTest {
        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/votes" -> {
                    respond(
                        content = buildJsonObject {
                            put("id", "dQw4w9WgXcQ")
                            put("likes", 1000000)
                            put("dislikes", 1)
                            put("rawLikes", 1000000)
                            put("rawDislikes", 1)
                            put("dateCreated", "2023-01-01T00:00:00Z")
                            put("deleted", false)
                            put("rating", 0.999999)
                            put("viewCount", 100000)
                        }.toString(),
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }
                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }

        val client = RydClient(mockEngine)

        data.forEach {
            val votes = client.getVotes(it.videoId)
            assertEquals(it.likes, votes.likes)
            assertEquals(it.dislikes, votes.dislikes)
        }
    }

    @Test
    fun testVote() = runTest {
        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/interact/vote" -> {
                    respond(
                        content = """{"challenge":"BiCDTObtH5yq7+GTfRulAA==","difficulty":15}""",
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }
                "/interact/confirmVote" -> {
                    respond(
                        content = "true",
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }
                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }
        val client = RydClient(mockEngine)
        val userId = RydClient.generateUserId()

        data.forEach { (videoId) ->
            client.like(videoId, userId)
            client.dislike(videoId, userId)
            client.removeVote(videoId, userId)
        }
    }
}