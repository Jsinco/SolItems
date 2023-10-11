package dev.jsinco.solitems.events;

public enum Delay {

    FAST(40L),
    SLOW(1200L);

    private final long delay;

    Delay(long delay) {
        this.delay = delay;
    }

    public long getTime() {
        return delay;
    }
}
