package me.ishift.epicguard.bukkit.util.server;

public class Memory {
    private static long format(long value) {
        return value / 1048576;
    }

    public static long getUsage() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.maxMemory() - runtime.freeMemory());
    }

    public static long getTotal() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.maxMemory());
    }

    public static long getFree() {
        final Runtime runtime = Runtime.getRuntime();
        return format(runtime.freeMemory());
    }
}
