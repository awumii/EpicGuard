package pl.polskistevek.guard.bungee.util;

import pl.polskistevek.guard.bungee.GuardPluginBungee;

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
