package me.ishift.epicguard.core.util;

import me.ishift.epicguard.core.EpicGuard;

public final class UpdateChecker {
    private static final String CHECK_URL = "https://raw.githubusercontent.com/xishift/EpicGuard/master/files/version.info";
    private static String remoteVersion;
    private static boolean available;

    public static void checkForUpdates(EpicGuard epicGuard) {
        remoteVersion = URLUtils.readString(CHECK_URL);
        int latest = Integer.parseInt(remoteVersion.replace(".", ""));
        int current = Integer.parseInt(epicGuard.getMethodInterface().getVersion().replace(".", ""));

        available = latest > current;
    }

    public static boolean isAvailable() {
        return available;
    }

    public static String getRemoteVersion() {
        return remoteVersion;
    }

    private UpdateChecker() {}
}
