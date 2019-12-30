package io.github.polskistevek.epicguard.universal.check;

import io.github.polskistevek.epicguard.universal.AttackManager;
import io.github.polskistevek.epicguard.universal.ConfigProvider;

public class RejoinCheck {
    public static boolean check(String name) {
        if (ConfigProvider.FORCE_REJOIN){
            if (!AttackManager.rejoinData.contains(name)) {
                AttackManager.rejoinData.add(name);
                return true;
            }
        }
        return false;
    }
}
