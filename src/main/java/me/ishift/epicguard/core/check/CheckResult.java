package me.ishift.epicguard.core.check;

import me.ishift.epicguard.core.util.ChatUtils;

import java.util.List;

public class CheckResult {
    private final boolean detected;
    private final List<String> kickMessage;

    public CheckResult(boolean detected, List<String> kickMessage) {
        this.detected = detected;
        this.kickMessage = kickMessage;
    }

    public boolean isDetected() {
        return this.detected;
    }

    public String getKickMessage() {
        StringBuilder reason = new StringBuilder();
        for (String string : this.kickMessage) {
            reason.append(ChatUtils.colored(string)).append("\n");
        }
        return reason.toString();
    }

    public static CheckResult undetected() {
        return new CheckResult(false, null);
    }
}
