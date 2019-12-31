package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;

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
