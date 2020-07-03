package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.ChatUtils;

import java.util.List;

public abstract class Check {
    private final EpicGuard epicGuard;
    private final boolean blacklist;
    private final String kickMessage;

    public Check(EpicGuard epicGuard, boolean blacklist, List<String> kickMessage) {
        this.epicGuard = epicGuard;
        this.blacklist = blacklist;

        StringBuilder builder = new StringBuilder();
        for (String string : kickMessage) {
            builder.append(ChatUtils.colored(string)).append("\n");
        }
        this.kickMessage = builder.toString();
    }

    public EpicGuard getEpicGuard() {
        return this.epicGuard;
    }

    public PluginConfiguration getConfig() {
        return this.epicGuard.getConfig();
    }

    public String getKickMessage() {
        return this.kickMessage;
    }

    public boolean shouldBlacklist() {
        return this.blacklist;
    }

    /**
     * @return true if detection is positive (detected as bot).
     */
    public abstract boolean check(String address, String nickname);
}
