package dev.zt64.ryd.model

import kotlinx.serialization.Serializable
import org.kotlincrypto.hash.sha2.SHA512
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.pow

/**
 * Represents a puzzle that must be solved to vote
 * @property challenge The challenge to solve
 * @property difficulty The difficulty of the puzzle
 */
@Serializable
public data class Puzzle internal constructor(val challenge: String, val difficulty: Int) {
    /**
     * Solves the puzzle
     *
     * @return A base64 encoded string representing the solution
     */
    @OptIn(ExperimentalEncodingApi::class)
    public fun solve(): String {
        val challenge = Base64.decode(challenge)
        val buffer = ByteArray(20)

        for (i in 4 until 20) buffer[i] = challenge[i - 4]

        val sha = SHA512()
        val maxCount = (2.toFloat().pow(difficulty) * 5).toInt()

        repeat(maxCount) { i ->
            val newBuffer = buffer.copyOf()
            newBuffer[0] = i.toByte()
            newBuffer[1] = (i ushr 8).toByte()
            newBuffer[2] = (i ushr 16).toByte()
            newBuffer[3] = (i ushr 24).toByte()

            val zeros = countLeadingZeroes(sha.digest(newBuffer))
            if (zeros >= difficulty) {
                return Base64.encode(newBuffer.sliceArray(0 until 4))
            }
        }

        throw IllegalStateException("Failed to solve puzzle")
    }
}

/**
 * Counts the number of leading zeroes in a byte array
 *
 * @param bytes The byte array to count the leading zeroes in
 * @return The number of leading zeroes
 */
internal fun countLeadingZeroes(bytes: ByteArray): Int {
    var zeroes = 0

    for (b in bytes) {
        if (b == 0.toByte()) {
            zeroes += 8
        } else {
            zeroes += b.countLeadingZeroBits()
            break
        }
    }

    return zeroes
}