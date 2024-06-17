import ar.edu.itba.eda.Timer.TimerJoda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerJodaTest {
    TimerJoda timer;

    @Test
    @DisplayName("Probar si funciona la duracion")
    void getDurationTest() {
        TimerJoda timer = new TimerJoda(0);
        timer.stop(2000);
        long expected = 2000;
        long result = timer.getElapsedTime();
        assertEquals(expected, result, "El tiempo transcurrido no es el correcto");
    }

    @Test
    @DisplayName("Probar si funciona el stop")
    void stopParamTest() {
        TimerJoda timer = new TimerJoda(0);
        timer.stop(2000);
        long expected = 2000;
        long result = timer.getStop();
        assertEquals(expected, result, "El stop no es correcto");
    }
}