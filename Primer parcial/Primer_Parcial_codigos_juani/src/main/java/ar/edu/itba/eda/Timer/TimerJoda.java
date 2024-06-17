package ar.edu.itba.eda.Timer;

import org.joda.time.Instant;
import org.joda.time.Period;

public class TimerJoda {
    private final Instant start;
    private Instant end;
    private boolean hasStopped = false;
    private boolean hasStarted = false;
    private long elapsedTime;

    public TimerJoda() {
        this.start = new Instant();
        hasStarted = true;
    }

    public TimerJoda(long ms) {
        this.start = new Instant(ms);
        hasStarted = true;
    }

    public void stop() {

        if(!hasStopped) {
            this.end = new Instant();
        }
        checkDuration();
        hasStopped = true;
    }

    public void stop(long ms) {

        if(!hasStopped) {
            this.end = new Instant(ms);
        }
        checkDuration();
        hasStopped = true;
    }

    public long getStop() {
        return end.getMillis();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    private void checkDuration() {
        if(end.isBefore(start)) {
            throw new RuntimeException("Stop cant be before start");
        }
        elapsedTime = end.getMillis() - start.getMillis();
    }

    public String toString() {
        if(!hasStopped) {
            throw new RuntimeException("Timer hasnt stopped");
        }
        if(!hasStarted) {
            throw new RuntimeException("Timer hasnt been started");
        }
        long duration = end.getMillis() - start.getMillis();
        Period period = new Period(this.start, this.end);
        return "(%d ms) %d days %d hours %d minutes %d.%03d seconds".formatted(duration, period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds(), period.getMillis());
    }
}
