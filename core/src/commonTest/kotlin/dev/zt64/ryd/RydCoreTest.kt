package dev.zt64.ryd

import dev.zt64.ryd.model.Puzzle
import dev.zt64.ryd.model.countLeadingZeroes
import kotlin.test.Test
import kotlin.test.assertEquals

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

class RydCoreTest {
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