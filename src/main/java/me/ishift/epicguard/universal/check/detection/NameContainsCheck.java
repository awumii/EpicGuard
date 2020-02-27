package me.ishift.epicguard.universal.check.detection;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.Check;
import me.ishift.epicguard.universal.types.Reason;

public class NameContainsCheck extends Check {
    public NameContainsCheck() {
        super(Reason.NAMECONTAINS, true);
    }

    @Override
    public boolean perform(String address, String nickname) {
        return Config.blockedNames.stream().anyMatch(string -> nickname.toLowerCase().contains(string.toLowerCase()));
    }
}
