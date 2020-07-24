package me.ishift.epicguard.core.check;

public class CheckResult {
    private final boolean detected;
    private final String kickMessage;

    public CheckResult(boolean detected, String kickMessage) {
        this.detected = detected;
        this.kickMessage = kickMessage;
    }

    public boolean isDetected() {
        return this.detected;
    }

    public String getKickMessage() {
        return this.kickMessage;
    }

    public static CheckResult undetected() {
        return new CheckResult(false, null);
    }
}
