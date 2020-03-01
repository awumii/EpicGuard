package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.AttackSpeed;
import me.ishift.epicguard.universal.Config;

import java.util.ArrayList;
import java.util.List;

public class ServerListCheck {
    private static List<String> pingList = new ArrayList<>();

    public static boolean perform(String address) {
        if (Config.serverListCheck && AttackSpeed.isUnderAttack()) {
            return !pingList.contains(address);
        }
        return false;
    }

    public static void addAddress(String address) {
        if (!pingList.contains(address)) pingList.add(address);
    }
}
