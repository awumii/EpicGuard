package me.ishift.epicguard.common.antibot.check.checks;

import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.check.Check;
import me.ishift.epicguard.common.data.config.Configuration;

public class NicknameCheck implements Check {
    @Override
    public boolean execute(String address, String nickname, AttackManager attackManager) {
        return Configuration.blockedNames.stream().anyMatch(string -> nickname.toLowerCase().contains(string.toLowerCase()));
    }
}
