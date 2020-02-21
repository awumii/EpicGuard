package me.ishift.epicguard.universal.util;

import me.ishift.epicguard.universal.Config;

import java.io.IOException;

public class RuntimeExecutor {
    public static void execute(String command) {
        if (!Config.firewallEnabled) return;
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void blacklist(String adress) {
        execute(Config.firewallBlacklistCommand.replace("{IP}", adress));
    }

    public static void whitelist(String adress) {
        execute(Config.firewallWhitelistCommand.replace("{IP}", adress));
    }
}
