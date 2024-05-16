package dev.zt64.ryd

import dev.zt64.ryd.model.Puzzle
import dev.zt64.ryd.model.countLeadingZeroes
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
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
                        content = """{"id":"dQw4w9WgXcQ","likes":1000000,"dislikes":1,"dateCreated":"2023-01-01T00:00:00Z","deleted":false,"rating":0.999999,"viewCount":1000001}""",
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }

        val client = RydClient(mockEngine)

        data.forEach {
            val votes = client.get(it.videoId)
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

    @Test
    fun testPuzzleSolver() {
        data.forEach { (_, challenge, solution) ->
            val puzzle = Puzzle(challenge, 15)
            assertEquals(solution, puzzle.solve(), "Failed to solve puzzle")
        }
    }

    @Test
    fun testCountLeadingZeros() {
        assertEquals(13, countLeadingZeroes(byteArrayOf(0, 7, 9, 1, 27, 12, 3, 4)))
    }
}