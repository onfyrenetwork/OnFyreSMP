package net.onfyre.onfyresmp.utils;

public class ServerLagController implements Runnable {
    public static int TICK_COUNT = 0;
    public static long[] TICKS = new long[600];
    public static long LAST_TICK = 0L;

    public static float getTPS() {
        return getTPS(100);
    }

    public static float getTPS(int ticks) {
        if (TICK_COUNT< ticks) {
            return 20.0F;
        }
        int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0F);
    }

    public static long getElapsed(int tickID) {
        if (TICK_COUNT- tickID >= TICKS.length) {

        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void run() {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT+= 1;
    }
}