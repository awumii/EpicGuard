package me.ishift.epicguard.core.handler;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckResult;
import me.ishift.epicguard.core.check.impl.*;
import me.ishift.epicguard.core.user.BotUser;
import me.ishift.epicguard.core.util.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class DetectionHandler {
    private final List<Check> checks = new ArrayList<>();
    private final EpicGuard epicGuard;

    public DetectionHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;

        this.checks.add(new AttackCheck(epicGuard));
        this.checks.add(new BlacklistCheck(epicGuard));
        this.checks.add(new GeographicalCheck(epicGuard));
        this.checks.add(new ReconnectCheck(epicGuard));
        this.checks.add(new AccountLimitCheck(epicGuard));
        this.checks.add(new ProxyCheck(epicGuard));
    }

    public CheckResult handle(String address, String nickname) {
        this.epicGuard.addConnectionPerSecond();
        if (this.epicGuard.getConnectionPerSecond() > this.epicGuard.getConfig().maxCps) {
            this.epicGuard.setAttack(true);
        }

        if (this.epicGuard.getStorageManager().isWhitelisted(address)) {
            return CheckResult.undetected();
        }

        BotUser user = new BotUser(address, nickname);
        for (Check check : this.checks) {
            if (check.check(user)) {
                if (check.blacklist()) {
                    this.epicGuard.getStorageManager().blacklist(address);
                }

                StringBuilder reason = new StringBuilder();
                for (String string : check.reason()) {
                    reason.append(ChatUtils.colored(string)).append("\n");
                }
                return new CheckResult(true, reason.toString());
            }
        }
        return CheckResult.undetected();
    }
}
