package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.ChatUtils;

import java.util.List;

public abstract class Check {
    private final EpicGuard epicGuard;

    public Check(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public EpicGuard getEpicGuard() {
        return this.epicGuard;
    }

    public PluginConfiguration getConfig() {
        return this.epicGuard.getConfig();
    }

    public abstract List<String> getKickMessage();

    public abstract boolean blacklistUser();

    /**
     * @return true if detection is positive (detected as bot).
     */
    public abstract boolean check(String address, String nickname);
}
