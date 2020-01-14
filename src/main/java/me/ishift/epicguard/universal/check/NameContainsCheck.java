package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;

public class NameContainsCheck {
    public static boolean check(String name) {
        for (String string : Config.blockedNames) {
            if (name.toLowerCase().contains(string.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
