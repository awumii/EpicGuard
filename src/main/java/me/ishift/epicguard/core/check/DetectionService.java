package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.impl.BlacklistCheck;
import me.ishift.epicguard.core.check.impl.GeographicalCheck;

import java.util.ArrayList;
import java.util.List;

public abstract class DetectionService {
    private final EpicGuard epicGuard;
    private final List<Check> checks;

    public DetectionService(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
        this.checks = new ArrayList<>();
        this.checks.add(new GeographicalCheck(epicGuard));
        this.checks.add(new BlacklistCheck(epicGuard));
    }

    /**
     * Returns true if the detection is positive (player should be kicked)
     */
    public boolean performCheck(String address, String nickname) {
        for (Check check : this.checks) {
            if (check.check(address, nickname)) {
                if (check.shouldBlacklist()) {
                    this.epicGuard.getStorageManager().blacklist(address);
                }
                return true;
            }
        }
        return false;
    }
}
