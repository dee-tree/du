import du.Measure
import du.MeasurementFormats
import du.Measurements
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MeasureTest {

    @Test
    fun measures() {
        assertEquals(Measurements.KILOBYTE to 1L, Measure(1024, Measure.MRDif).eval(MeasurementFormats.NORMAL))
        assertEquals(Measurements.KILOBYTE to 15L, Measure(15 * 1024, Measure.MRDif).eval(MeasurementFormats.NORMAL))
        assertEquals(Measurements.KILOBYTE to 1L, Measure(1000, Measure.HRDif).eval(MeasurementFormats.NORMAL))
        assertEquals(Measurements.KILOBYTE to 1200L, Measure(1200 * 1000, Measure.HRDif).eval(MeasurementFormats.NORMAL))

        assertEquals(Measurements.BYTE to 100L, Measure(100, Measure.HRDif).eval(MeasurementFormats.HUMAN_READABLE))
        assertEquals(Measurements.KILOBYTE to 1L, Measure(1024, Measure.HRDif).eval(MeasurementFormats.HUMAN_READABLE))
        assertEquals(Measurements.KILOBYTE to 1L, Measure(1024, Measure.HRDif).eval(MeasurementFormats.HUMAN_READABLE))
        assertEquals(Measurements.KILOBYTE to 1L, Measure((1024 * 1.6).toLong(), Measure.HRDif).eval(MeasurementFormats.HUMAN_READABLE))
        assertEquals(
            Measurements.MEGABYTE to 1L, Measure((1024 * 1024 * 1.6).toLong(), Measure.HRDif).eval(
                MeasurementFormats.HUMAN_READABLE))
    }
}