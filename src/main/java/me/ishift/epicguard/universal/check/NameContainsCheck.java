package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.types.Reason;

public class NameContainsCheck {
    public static boolean perform(String nickname) {
        return Config.blockedNames.stream().anyMatch(string -> nickname.toLowerCase().contains(string.toLowerCase()));
    }
}
