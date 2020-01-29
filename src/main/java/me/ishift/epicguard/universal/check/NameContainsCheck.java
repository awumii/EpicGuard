package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;

public class NameContainsCheck {
    public static boolean check(String name) {
        return Config.blockedNames.stream().anyMatch(string -> name.toLowerCase().contains(string.toLowerCase()));
    }
}
