import ar.edu.itba.eda.Timer.TimerNativo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class TimerNativoTest {

    @Test
    @DisplayName("Probar si el lapso de tiempo es correcto")
    void getDurationTest() {
        TimerNativo timer = new TimerNativo(0);
        timer.stop(93623040);
        long expected = 93623040;
        long result = timer.getDuration();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Probar excepcion de stop")
    void stopExceptionTest() {
        TimerNativo timer = new TimerNativo(100);
        assertThrows(RuntimeException.class, () -> timer.stop(5));
    }

    @Test
    void testToString() {
        TimerNativo timer = new TimerNativo(0);
        timer.stop(93623040);
        long expectedDays = 1;
        long expectedHours = 2;
        long expectedMinutes = 0;
        long expectedSeconds = 23;
        long expectedMillis = 40;
        long expectedTotMillis = 93623040;
        assertEquals(expectedDays, timer.getDays());
        assertEquals(expectedHours, timer.getHours());
        assertEquals(expectedMinutes, timer.getMinutes());
        assertEquals(expectedSeconds, timer.getSeconds());
        assertEquals(expectedMillis, timer.getMillis());
        assertEquals(expectedTotMillis, timer.getTotalMillis());
    }
}