package me.ishift.epicguard.core.task;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.UpdateChecker;

public class UpdateCheckerTask implements Runnable {
    private final EpicGuard epicGuard;

    public UpdateCheckerTask(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void run() {
        UpdateChecker.checkForUpdates(this.epicGuard);

        if (UpdateChecker.isAvailable()) {
            this.epicGuard.getLogger().info("New update is available");
        }
    }
}
