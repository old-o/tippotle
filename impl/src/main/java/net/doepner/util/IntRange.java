package net.doepner.util;

/**
 * An int value that moves step by step through a range
 */
public final class IntRange {

    private final int min;
    private final int max;
    private final int step;

    private int value;
    private boolean forward = true;

    public IntRange(int min, int max, int startValue, int step) {
        if (max <= min) {
            throw new IllegalArgumentException("Please ensure: min < max");
        }
        this.min = min;
        this.max = max;
        if (step >= max - min) {
            throw new IllegalArgumentException("Please ensure: step < max - min");
        }
        this.step = step;
        if (startValue < min || startValue > max) {
            throw new IllegalArgumentException("Please ensure: min <= startValue <= max");
        }
        value = startValue;
    }

    public int nextValue() {
        final int next = calcNextValue();
        if (next > max || next < min) {
            forward = !forward;
            value += forward ? 1 : -1;
        }
        value = calcNextValue();
        return value;
    }

    private int calcNextValue() {
        return forward ? value + step : value - step;
    }
}
