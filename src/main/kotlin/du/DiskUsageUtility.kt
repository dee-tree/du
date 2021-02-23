package du

import com.xenomachina.argparser.SystemExitException
import du.Measure.*
import java.io.File

/**
 * @author Dmitriy Sokolov
 *
 * Class realized disk-usage utility
 *
 * @property h is a flag determining should the size be displayed in a human-readable format (specific unit) or not (KB)
 * @property c is a flag determining should be displayed the total size of all files as one value or not (one value per one file)
 * @property si is a flag determining should be used SI or not (IEC)
 * @property files are files whose names passed in the <tt>constructor</tt>
 *
 * @throws SystemExitException if even one file in <tt>files</tt> does not exist
 *
 * @constructor creates an object of <tt>DiskUsageUtility</tt> for <tt>files</tt>
 */
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

    /**
     * Returns map where key is filename and value is a pair of unit and amount in this unit of sizes of these <tt>files</tt>
     * @return map where key is filename and value is a pair of unit and amount in this unit of sizes of these <tt>files</tt>
     */
    fun measurements(): Map<String, Pair<Units, Long>> {
        val measurements = mutableMapOf<String, Pair<Units, Long>>()
        val difference = if (si) Standards.SI else Standards.IEC
        val format = if (h) Formats.HUMAN_READABLE else Formats.NORMAL
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

    /**
     * @return total size in bytes of these <tt>files</tt>
     */
    private fun bytes(): Long = files.fold(0) {acc: Long, file -> acc + bytes(file) }

    /**
     * Returns amount in bytes of the file's size. If it's directory, returns total size of all files inside this directory
     * @param file is a file which size will be given
     * @return amount in bytes of the file's size
     */
    private fun bytes(file: File): Long = file.walkTopDown().fold(0) {acc: Long, leafFile -> acc + leafFile.length() }
}