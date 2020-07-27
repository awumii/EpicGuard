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

package me.ishift.epicguard.core.handler;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.check.Check;
import me.ishift.epicguard.core.check.CheckResult;
import me.ishift.epicguard.core.check.impl.*;
import me.ishift.epicguard.core.user.BotUser;
import me.ishift.epicguard.core.util.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class DetectionHandler {
    private final List<Check> checks = new ArrayList<>();
    private final EpicGuard epicGuard;

    public DetectionHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;

        this.checks.add(new AttackCheck(epicGuard));
        this.checks.add(new BlacklistCheck(epicGuard));
        this.checks.add(new GeographicalCheck(epicGuard));
        this.checks.add(new ServerListCheck(epicGuard));
        this.checks.add(new ReconnectCheck(epicGuard));
        this.checks.add(new AccountLimitCheck(epicGuard));
        this.checks.add(new ProxyCheck(epicGuard));
    }

    public CheckResult handle(String address, String nickname) {
        this.epicGuard.addConnectionPerSecond();
        if (this.epicGuard.getConnectionPerSecond() > this.epicGuard.getConfig().maxCps) {
            this.epicGuard.setAttack(true);
        }

        if (this.epicGuard.getStorageManager().isWhitelisted(address)) {
            return CheckResult.undetected();
        }

        BotUser user = new BotUser(address, nickname);
        for (Check check : this.checks) {
            if (check.check(user)) {
                if (check.blacklist()) {
                    this.epicGuard.getStorageManager().blacklist(address);
                }
                return new CheckResult(true, check.reason());
            }
        }
        return CheckResult.undetected();
    }
}
