package pl.polskistevek.guard.bungee.util;

import pl.polskistevek.guard.bungee.BungeeMain;

import java.io.IOException;

public class FirewallManager {
    public static void blacklist(String adress) {
        if (BungeeMain.FIREWALL) {
            try {
                Runtime.getRuntime().exec(BungeeMain.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void whitelist(String adress) {
        if (BungeeMain.FIREWALL) {
            try {
                Runtime.getRuntime().exec(BungeeMain.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
