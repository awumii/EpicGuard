package io.github.polskistevek.epicguard.bungee.util;

import io.github.polskistevek.epicguard.bungee.GuardBungee;

import java.io.IOException;

public class FirewallManager {
    public static void blacklist(String adress) {
        if (GuardBungee.FIREWALL) {
            try {
                Runtime.getRuntime().exec(GuardBungee.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void whitelist(String adress) {
        if (GuardBungee.FIREWALL) {
            try {
                Runtime.getRuntime().exec(GuardBungee.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
