package me.ishift.epicguard.common.antibot.check;

public interface Check {
    boolean execute(String address, String nickname);
}
