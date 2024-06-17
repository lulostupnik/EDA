package ar.edu.itba.eda.Timer;

public class TimerPropio {
    private final long start;
    private long end;

    public TimerPropio() {
        this.start = System.currentTimeMillis();
        this.end = -1;
    }

    public TimerPropio(long start) {
        this.start = start;
    }

    public long getElapsedTime() {
        return this.end - this.start;
    }

    public void stop() {
        long end = System.currentTimeMillis();
        if(end < this.start) {
            throw new RuntimeException("Inválido");
        }
        this.end = end;
    }

    public void stop(long end) {
        if(end < this.start) {
            throw new RuntimeException("Inválido");
        }
        this.end = end;
    }

    @Override
    public String toString() {
        if(this.end == -1) {
            throw new RuntimeException("No se frenó el timer");
        }
        long dur = this.end - this.start;
        long durr = dur;
        long days = dur / 1000 / 60 / 60 / 24;
        dur -= days * 86400000;
        long hours = dur / 1000 / 60 / 60;
        dur -= hours * 3600000;
        long minutes = dur / 1000 / 60;
        dur -= minutes * 60000;
        return "(%d ms) %d día %d hs %d min %.3f s".formatted(durr, days, hours, minutes, (float)dur / 1000);
    }
}


