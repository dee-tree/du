package du

import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

data class Measure(val bytes: Long, val difference: Int) {

    companion object {
        /**
         * Machine-readable difference
         */
        const val MRDif = 1024

        /**
         * Human-readable difference
         */
        const val HRDif = 1000
    }


    init {
        if (bytes < 0)
            throw IllegalArgumentException("Bytes count must be non-negative, but actual: $bytes")

        if (difference <= 1)
            throw IllegalArgumentException("difference must be positive and greater than 1, but actual: $difference")
    }

    fun eval(format: MeasurementFormats): Pair<Measurements, Long> {

        val measurements = Measurements.values()
        var measurementIdx = 0

        val value = when (format) {
            MeasurementFormats.NORMAL -> {
                measurementIdx++
                bytes / difference
            }

            MeasurementFormats.HUMAN_READABLE -> {
                val power = floor(log(bytes.toDouble(), difference.toDouble())).toInt()

                measurementIdx += power

                bytes / (difference.toDouble().pow(power.toDouble()).toInt())
            }
        }

        return measurements[measurementIdx] to value
    }
}


// Long may store 63 bits + 1 sign. Petabyte is 2^53 bytes
enum class Measurements {
    BYTE, KILOBYTE, MEGABYTE, GIGABYTE, TERABYTE, PETABYTE;

    companion object {
        fun toString(measurement: Measurements): String = when (measurement) {
            BYTE -> "B"
            KILOBYTE -> "KB"
            MEGABYTE -> "MB"
            GIGABYTE -> "GB"
            TERABYTE -> "TB"
            PETABYTE -> "PB"
        }
    }
}

/**
 * <tt>NORMAL</tt>: in KiloBytes
 * <tt>HUMAN_READABLE</tt>: In specific measure, where number is in standard kind: 1 <= x < difference
 */
enum class MeasurementFormats {
    NORMAL, HUMAN_READABLE
}