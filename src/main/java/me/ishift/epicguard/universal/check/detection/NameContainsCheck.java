package me.ishift.epicguard.universal.check.detection;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.Check;
import me.ishift.epicguard.universal.types.Reason;

public class NameContainsCheck extends Check {
    public NameContainsCheck() {
        super(Reason.NAMECONTAINS, true);
    }

    public static boolean check(String name) {
        return Config.blockedNames.stream().anyMatch(string -> name.toLowerCase().contains(string.toLowerCase()));
    }

    @Override
    public boolean perform(String address, String nickname) {
        return false;
    }
}
