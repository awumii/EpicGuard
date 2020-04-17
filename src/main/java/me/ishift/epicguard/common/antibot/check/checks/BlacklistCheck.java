package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.StorageManager;

public class BlacklistCheck implements Check {
    @Override
    public boolean execute(String address, String nickname, AttackManager attackManager) {
        return StorageManager.getStorage().getBlacklist().contains(address);
    }
}
