package me.ishift.epicguard.universal.check;

import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.AttackSpeed;

import java.util.ArrayList;
import java.util.List;

public class VerifyCheck {
    private static List<String> rejoined = new ArrayList<>();

    public static boolean perform(String nickname) {
        if (Config.rejoinCheck && AttackSpeed.isUnderAttack()) {
            if (!rejoined.contains(nickname)) {
                rejoined.add(nickname);
                return true;
            }
        }
        return false;
    }
}
