package pl.polskistevek.guard.utils;

public class ServerUtils {
    private static String humanReadableByteCount(final long bytes) {
        if (bytes < 1536L) {
            return bytes + " B";
        }
        final int exp = (int) (Math.log((double) bytes) / Math.log(1536.0));
        final String pre = String.valueOf("KMGTPE".charAt(exp - 1));
        return String.format("%.2f %sB", bytes / Math.pow(1024.0, exp), pre);
    }

    public static String getRamUsage(){
        final long freeMemory = Runtime.getRuntime().freeMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        return humanReadableByteCount(totalMemory - freeMemory);
    }

    public static String getRam(){
        return humanReadableByteCount(Runtime.getRuntime().totalMemory());
    }
}
