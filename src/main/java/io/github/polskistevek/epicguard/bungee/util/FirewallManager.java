package io.github.polskistevek.epicguard.bungee.util;

import io.github.polskistevek.epicguard.bungee.GuardPluginBungee;

import java.io.IOException;

public class FirewallManager {
    public static void blacklist(String adress) {
        if (GuardPluginBungee.FIREWALL) {
            try {
                Runtime.getRuntime().exec(GuardPluginBungee.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void whitelist(String adress) {
        if (GuardPluginBungee.FIREWALL) {
            try {
                Runtime.getRuntime().exec(GuardPluginBungee.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
