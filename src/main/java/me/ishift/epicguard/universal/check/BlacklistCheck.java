package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.types.Reason;

public class BlacklistCheck {
    public static boolean perform(String address) {
        return StorageManager.isBlacklisted(address);
    }
}
