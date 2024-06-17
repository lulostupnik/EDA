package ar.edu.itba.eda.Timer;
import java.time.Duration;
import java.time.Instant;

public class TimerNativo {
    private final long start;
    private long end;
    private Duration duration;
    private boolean hasStarted = false;

    public TimerNativo() {
        hasStarted = true;
        this.start = Instant.now().toEpochMilli();
    }

    public TimerNativo(long start) {
        hasStarted = true;
        this.start = start;
    }

    public void stop(long end) {
        if(!hasStarted) {
            throw new RuntimeException("Timer isnt running");
        }
        if(end < start) {
            throw new RuntimeException("Invalid times");
        }
        duration = Duration.ofMillis(end - start);
        hasStarted = false;
        this.end = end;
    }

    public long getDuration() {
        return Duration.ofMillis(end - start).toMillis();
    }

    public void stop() {
        stop(Instant.now().toEpochMilli());
    }

    public String toString() {
        return "(%d ms) %d days %d hours %d minutes %d.%03d seconds".formatted(getTotalMillis(), getDays(), getHours(), getMinutes(), getSeconds(), getMillis());
    }

    public long getDays() {
        return duration.toDaysPart();
    }

    public long getHours() {
        return duration.toHoursPart();
    }
    public long getMinutes() {
        return duration.toMinutesPart();
    }

    public long getSeconds() {
        return duration.toSecondsPart();
    }

    public long getTotalMillis() {
        return duration.toMillis();
    }

    public long getMillis() {
        return duration.toMillisPart();
    }
}
