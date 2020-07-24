package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.user.BotUser;

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

    public MessagesConfiguration getMessages() {
        return this.epicGuard.getMessages();
    }

    public StorageManager getStorage() {
        return this.epicGuard.getStorageManager();
    }

    public boolean isAttack() {
        return this.epicGuard.isAttack();
    }

    public abstract List<String> reason();

    public abstract boolean blacklist();

    /**
     * @return true if detection is positive (detected as bot).
     */
    public abstract boolean check(BotUser user);
}
