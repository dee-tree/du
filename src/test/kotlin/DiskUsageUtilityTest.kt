import du.Measure
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

import du.Measure.Units.*
import kotlin.test.assertTrue

class DiskUsageUtilityTest {

    private fun createFile(filename: String, text: String) {
        File(filename).printWriter().use { out ->
            out.print(text)
        }
    }

    private fun deleteFile(filename: String) {
        File(filename).delete()
    }


    private fun assertMeasurement(expected: Pair<Measure.Units, Long>, actual: Map<String, Pair<Measure.Units, Long>>) {
        assertTrue(actual.size == 1, "Expected size 1, but was: ${actual.size}")
        assertEquals(expected, actual.values.first())
    }

    private fun assertMeasurements(expected: Map<String, Pair<Measure.Units, Long>>, actual: Map<String, Pair<Measure.Units, Long>>) {
        assertEquals(expected.size, actual.size, "Expected size ${expected.size}, but was given: ${actual.size}")

        for (expEntry in expected) {
            assertEquals(expEntry.value, actual[File(expEntry.key).path])
        }
    }


    @Test
    fun testUtility() {
        val text1 = "Hello world"
        val text2 = text1.repeat(20)
        val text3 = "a".repeat(1000)

        createFile("test", text1)

        assertMeasurement(B to text1.length.toLong(), du.DiskUsageUtility(true, false, false, "test").measurements())

        assertMeasurement(KB to 0L, du.DiskUsageUtility(false, true, false, "test").measurements())

        createFile("test2", text2)
        assertMeasurement(B to (text1.length + text2.length).toLong(), du.DiskUsageUtility(true, true, false, "test", "test2").measurements())

        val meas4 = du.DiskUsageUtility(true, false, false, "test", "test2").measurements()
        assertMeasurements(mapOf("test" to (B to text1.length.toLong()), "test2" to (B to text2.length.toLong())), meas4)

        createFile("test3", text3)
        assertMeasurement(KB to 1, du.DiskUsageUtility(true, false, true, "test3").measurements())
        assertMeasurement(KB to 1, du.DiskUsageUtility(false, false, true, "test3").measurements())
        assertMeasurement(KB to 1, du.DiskUsageUtility(false, true, true, "test3").measurements())
        assertMeasurement(KB to 1, du.DiskUsageUtility(true, true, true, "test3").measurements())
        assertMeasurement(B to 1000, du.DiskUsageUtility(true, true, false, "test3").measurements())
        assertMeasurement(KB to 0, du.DiskUsageUtility(false, true, false, "test3").measurements())

        deleteFile("test")
        deleteFile("test2")
        deleteFile("test3")
    }
}