package me.ishift.epicguard.universal.check.detection;

import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.Check;
import me.ishift.epicguard.universal.types.Reason;

public class BlacklistCheck extends Check {
    public BlacklistCheck() {
        super(Reason.BLACKLIST, false);
    }

    @Override
    public boolean perform(String address, String nickname) {
        return StorageManager.isBlacklisted(address);
    }
}
