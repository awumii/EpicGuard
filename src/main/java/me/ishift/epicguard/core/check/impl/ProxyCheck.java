package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;
import me.ishift.epicguard.core.user.BotUser;
import me.ishift.epicguard.core.util.URLUtils;

import java.util.List;

public class ProxyCheck extends Check implements Runnable {
    private int requests;

    public ProxyCheck(EpicGuard epicGuard) {
        super(epicGuard);
        epicGuard.getMethodInterface().scheduleAsyncTask(this, 86400L); //24 hours
    }

    @Override
    public boolean check(BotUser user) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().proxyCheck);

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return this.proxyCheck(user.getAddress());
            case ATTACK:
                if (this.getEpicGuard().isAttack()) {
                    return this.proxyCheck(user.getAddress());
                }
        }
        return false;
    }

    private boolean proxyCheck(String address) {
        if (this.requests > this.getConfig().requestLimit) {
            return false;
        }

        String url = "http://proxycheck.io/v2/" + address + "?key=" + this.getConfig().proxyCheckKey + "&vpn=1";
        if (!this.getConfig().customProxyCheck.equals("disabled")) {
            url = this.getConfig().customProxyCheck.replace("%ip%", address);
        }

        this.requests++;
        return URLUtils.readString(url).contains("yes");
    }

    @Override
    public void run() {
        this.requests = 0;
    }

    @Override
    public List<String> getKickMessage() {
        return this.getEpicGuard().getMessages().kickMessageProxy;
    }

    @Override
    public boolean blacklistUser() {
        return true;
    }
}
