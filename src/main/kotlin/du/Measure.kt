package du

import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

/**
 * @author Dmitriy Sokolov
 *
 * Stores bytes and allows to convert them to specific unit
 * @property bytes initial value of bytes
 * @property kind kind of measure
 */
data class Measure(val bytes: Long, val kind: Standards) {

    private val difference = kind.diff

    enum class Units {
        B, KB, MB, GB, TB, PB;
    }

    /**
     * The value to multiply the original value to get KB from B
     * <tt>SI</tt>: 1 KB = 1000 B
     * <tt>IEC</tt>: 1 KB = 1024 B
     */
    enum class Standards(val diff: Int) {
        SI(1000), IEC(1024)
    }

    /**
     * <tt>NORMAL</tt>: in Kilobytes
     * <tt>HUMAN_READABLE</tt>: In specific measure, where number is in standard kind: 1 <= x < difference
     */
    enum class Formats {
        NORMAL, HUMAN_READABLE
    }


    init {
        if (bytes < 0)
            throw IllegalArgumentException("Bytes count must be non-negative, but actual: $bytes")

        if (difference <= 1)
            throw IllegalArgumentException("difference must be positive and greater than 1, but actual: $difference")
    }

    /**
     * evaluates these bytes to specific unit
     * @param format is a format for return value
     * @return pair of unit and amount in this unit for these bytes
     */
    fun eval(format: Formats): Pair<Units, Long> {

        val measurements = Units.values()
        var measurementIdx = 0

        val value = when (format) {
            Formats.NORMAL -> {
                measurementIdx++
                bytes / difference
            }

            Formats.HUMAN_READABLE -> {
                val power = floor(log(bytes.toDouble(), difference.toDouble())).toInt()

                measurementIdx += power

                bytes / (difference.toDouble().pow(power.toDouble()).toInt())
            }
        }

        return measurements[measurementIdx] to value
    }
}
