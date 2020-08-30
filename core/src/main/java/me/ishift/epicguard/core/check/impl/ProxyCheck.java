/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.ishift.epicguard.core.check.impl;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckMode;
import me.ishift.epicguard.core.user.BotUser;
import me.ishift.epicguard.core.util.URLUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProxyCheck extends Check implements Runnable {
    private int requests;

    public ProxyCheck(EpicGuard epicGuard) {
        super(epicGuard);
        epicGuard.getPlugin().scheduleTask(this, TimeUnit.HOURS.toSeconds(24));
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
                if (this.isAttack()) {
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
        return this.getMessages().kickMessageProxy;
    }

    @Override
    public boolean shouldBlacklist() {
        return this.getConfig().proxyCheckBlacklist;
    }
}
