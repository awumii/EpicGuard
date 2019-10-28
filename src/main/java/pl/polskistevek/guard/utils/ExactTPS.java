package pl.polskistevek.guard.utils;

import java.text.DecimalFormat;

public class ExactTPS implements Runnable {
    public static int TICK_COUNT= 0;
    public static long[] TICKS= new long[600];
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int ticks) {
        if (TICK_COUNT< ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];
        return ticks / (elapsed / 1000.0D);
    }

    public static String getTPS2(){
        return df2.format(getTPS());
    }

    public void run() {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();
        TICK_COUNT+= 1;
    }
}
