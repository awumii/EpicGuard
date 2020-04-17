package me.ishift.epicguard.common.antibot.check;

import me.ishift.epicguard.common.antibot.AttackManager;

public interface Check {
    boolean execute(String address, String nickname, AttackManager attackManager);
}
