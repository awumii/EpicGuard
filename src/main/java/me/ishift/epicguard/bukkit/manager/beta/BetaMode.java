package me.ishift.epicguard.bukkit.manager.beta;

import me.ishift.epicguard.bukkit.GuardBukkit;

public class BetaMode {
    private static boolean betaMode;

    public static void init() {
        betaMode = GuardBukkit.getInstance().getConfig().getBoolean("beta-layout");
    }

    public static boolean isBetaMode() {
        return betaMode;
    }
}
