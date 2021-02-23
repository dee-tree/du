import du.Measure
import du.Measure.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MeasureTest {

    @Test
    fun measures() {
        assertEquals(Units.KB to 1L, Measure(1024, Standards.IEC).eval(Formats.NORMAL))
        assertEquals(Units.KB to 15L, Measure(15 * 1024, Standards.IEC).eval(Formats.NORMAL))
        assertEquals(Units.KB to 1L, Measure(1000, Standards.SI).eval(Formats.NORMAL))
        assertEquals(Units.KB to 1200L, Measure(1200 * 1000, Standards.SI).eval(Formats.NORMAL))

        assertEquals(Units.B to 100L, Measure(100, Standards.SI).eval(Formats.HUMAN_READABLE))
        assertEquals(Units.KB to 1L, Measure(1024, Standards.SI).eval(Formats.HUMAN_READABLE))
        assertEquals(Units.KB to 1L, Measure(1024, Standards.SI).eval(Formats.HUMAN_READABLE))
        assertEquals(Units.KB to 1L, Measure((1024 * 1.6).toLong(), Standards.SI).eval(Formats.HUMAN_READABLE))
        assertEquals(Units.MB to 1L, Measure((1024 * 1024 * 1.6).toLong(), Standards.SI).eval(Formats.HUMAN_READABLE))
    }
}