package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;
import me.ishift.epicguard.core.util.URLUtils;

public class ProxyCheck extends Check {
    public ProxyCheck(EpicGuard epicGuard) {
        super(epicGuard, true, epicGuard.getMessages().kickMessageProxy);
    }

    @Override
    public boolean check(String address, String nickname) {
        CheckMode mode = CheckMode.valueOf(this.getConfig().proxyCheck);
        String url = "http://proxycheck.io/v2/" + address + "?key=" + this.getConfig().proxyCheckKey + "&vpn=1";

        switch (mode) {
            case NEVER:
                return false;
            case ALWAYS:
                return URLUtils.readString(url).contains("yes");
            case ATTACK:
                if (this.getEpicGuard().isAttack()) {
                    return URLUtils.readString(url).contains("yes");
                }
        }
        return false;
    }
}
