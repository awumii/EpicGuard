package me.ishift.epicguard.bukkit.util.server;

public class MemoryUtil {
    private static String format(final long bytes) {
        if (bytes < 1536L) {
            return bytes + " B";
        }
        final int exp = (int) (Math.log((double) bytes) / Math.log(1536.0));
        final String pre = String.valueOf("KMGTPE".charAt(exp - 1));
        return String.format("%.2f %sB", bytes / Math.pow(1024.0, exp), pre);
    }

    public static String getMemoryUsage() {
        final long freeMemory = Runtime.getRuntime().freeMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        return format(totalMemory - freeMemory);
    }

    public static String getTotalMemory() {
        return format(Runtime.getRuntime().totalMemory());
    }
}
