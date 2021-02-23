package du

import com.xenomachina.argparser.SystemExitException
import java.io.File

class DiskUsageUtility(
    private val h: Boolean,
    private val c: Boolean,
    private val si: Boolean,
    vararg filenames: String) {

    private val files: List<File> = filenames.map { File(it) }

    init {

        for (file in files) {
            if (!file.exists()) {
                throw SystemExitException("File ${file.absolutePath} does not exist!", 1)
            }
        }
    }

    fun measurements(): Map<String, Pair<Measurements, Long>> {
        val measurements = mutableMapOf<String, Pair<Measurements, Long>>()
        val difference = if (si) Measure.HRDif else Measure.MRDif
        val format = if (h) MeasurementFormats.HUMAN_READABLE else MeasurementFormats.NORMAL
        when (c) {
            true -> {
                measurements["Summarize size"] = Measure(bytes(), difference).eval(format)
            }

            false -> {
                files.forEach { file ->
                    measurements[file.path] = Measure(bytes(file), difference).eval(format)
                }
            }
        }
        return measurements
    }

    private fun bytes(): Long = files.fold(0) {acc: Long, file -> acc + bytes(file) }

    private fun bytes(file: File): Long = file.walkTopDown().fold(0) {acc: Long, leafFile -> acc + leafFile.length() }
}