package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;

public class ServerListCheck implements Check {
    @Override
    public boolean execute(String address, String nickname, AttackManager attackManager) {
        if (Configuration.serverListCheck && attackManager.isUnderAttack()) {
            return !StorageManager.getStorage().getPingData().contains(address);
        }
        return false;
    }
}
