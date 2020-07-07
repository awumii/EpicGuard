package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.impl.AttackCheck;
import me.ishift.epicguard.core.check.impl.BlacklistCheck;
import me.ishift.epicguard.core.check.impl.GeographicalCheck;
import me.ishift.epicguard.core.check.impl.ProxyCheck;

import java.util.ArrayList;
import java.util.List;

public abstract class DetectionService {
    private final EpicGuard epicGuard;
    private final List<Check> checks;

    private String kickMessage;

    public DetectionService(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
        this.checks = new ArrayList<>();
        this.checks.add(new AttackCheck(epicGuard));
        this.checks.add(new BlacklistCheck(epicGuard));
        this.checks.add(new GeographicalCheck(epicGuard));
        this.checks.add(new ProxyCheck(epicGuard));
    }

    public String getKickMessage() {
        return this.kickMessage;
    }

    /**
     * Returns true if the detection is positive (player should be kicked)
     */
    public boolean performCheck(String address, String nickname) {
        this.epicGuard.setConnectionPerSecond(this.epicGuard.getConnectionPerSecond() + 1);

        if (this.epicGuard.getConnectionPerSecond() > this.epicGuard.getConfig().maxCps) {
            this.epicGuard.setAttack(true);
        }

        if (this.epicGuard.getStorageManager().isWhitelisted(address)) {
            return false;
        }

        for (Check check : this.checks) {
            if (check.check(address, nickname)) {
                if (check.shouldBlacklist()) {
                    this.epicGuard.getStorageManager().blacklist(address);
                }
                this.kickMessage = check.getKickMessage();
                return true;
            }
        }
        return false;
    }
}
