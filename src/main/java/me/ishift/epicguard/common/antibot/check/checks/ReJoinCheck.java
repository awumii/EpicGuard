package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;

public class ReJoinCheck implements Check {
    private final AttackManager attackManager;

    public ReJoinCheck(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @Override
    public boolean execute(String address, String nickname) {
        if (Configuration.rejoinCheck && this.attackManager.isUnderAttack() && !StorageManager.getStorage().getRejoinData().contains(nickname)) {
            StorageManager.getStorage().getRejoinData().add(nickname);
            return true;
        }
        return false;
    }
}
