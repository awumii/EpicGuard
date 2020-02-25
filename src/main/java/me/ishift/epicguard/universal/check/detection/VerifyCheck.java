package me.ishift.epicguard.universal.check.detection;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.Check;
import me.ishift.epicguard.universal.types.Reason;

public class VerifyCheck extends Check {
    public VerifyCheck() {
        super(Reason.VERIFY, false);
    }

    @Override
    public boolean perform(String address, String nickname) {
        if (Config.forceRejoin) {
            if (!StorageManager.hasRejoined(nickname)) {
                StorageManager.addRejoined(nickname);
                return true;
            }
        }
        return false;
    }
}
